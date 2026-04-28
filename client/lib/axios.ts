import axios, { AxiosError, InternalAxiosRequestConfig } from "axios";
import type { AuthResponse } from "@/types/response";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080/api";

const ACCESS_TOKEN_KEY = "access_token";

interface RetryAxiosRequestConfig extends InternalAxiosRequestConfig {
    _retry?: boolean;
}

const isBrowser = typeof window !== "undefined";

const tokenStorage = {
    get(): string | null {
        if (!isBrowser) return null;
        return window.localStorage.getItem(ACCESS_TOKEN_KEY);
    },
    set(token: string): void {
        if (!isBrowser) return;
        window.localStorage.setItem(ACCESS_TOKEN_KEY, token);
    },
    clear(): void {
        if (!isBrowser) return;
        window.localStorage.removeItem(ACCESS_TOKEN_KEY);
    },
};

export const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    withCredentials: true,
    headers: {
        "Content-Type": "application/json",
    },
});

axiosInstance.interceptors.request.use((config) => {
    const token = tokenStorage.get();
    if (token) {
        config.headers = config.headers ?? {};
        (config.headers as Record<string, string>).Authorization = `Bearer ${token}`;
    }
    return config;
});

let isRefreshing = false;
let waitingQueue: Array<(token: string | null) => void> = [];

const releaseQueue = (token: string | null) => {
    waitingQueue.forEach((cb) => cb(token));
    waitingQueue = [];
};

const isAuthEndpoint = (url?: string): boolean => {
    if (!url) return false;
    return url.includes("/auth/login") || url.includes("/auth/register") || url.includes("/auth/refresh");
};

const requestRefreshToken = async (): Promise<string | null> => {
    const response = await axios.post<AuthResponse>(`${API_BASE_URL}/auth/refresh`, undefined, {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
    });

    return response.data?.accessToken ?? null;
};

axiosInstance.interceptors.response.use(
    (response) => response,
    async (error: AxiosError) => {
        const originalRequest = error.config as RetryAxiosRequestConfig | undefined;
        const status = error.response?.status;

        if (!originalRequest || status !== 401 || isAuthEndpoint(originalRequest.url)) {
            return Promise.reject(error);
        }

        if (originalRequest._retry) {
            return Promise.reject(error);
        }
        originalRequest._retry = true;

        if (isRefreshing) {
            return new Promise((resolve, reject) => {
                waitingQueue.push((newToken) => {
                    if (!newToken) {
                        reject(error);
                        return;
                    }
                    originalRequest.headers = originalRequest.headers ?? {};
                    (originalRequest.headers as Record<string, string>).Authorization = `Bearer ${newToken}`;
                    resolve(axiosInstance(originalRequest));
                });
            });
        }

        isRefreshing = true;
        try {
            const newToken = await requestRefreshToken();
            if (!newToken) {
                tokenStorage.clear();
                releaseQueue(null);
                return Promise.reject(error);
            }

            tokenStorage.set(newToken);
            releaseQueue(newToken);

            originalRequest.headers = originalRequest.headers ?? {};
            (originalRequest.headers as Record<string, string>).Authorization = `Bearer ${newToken}`;
            return axiosInstance(originalRequest);
        } catch (refreshError) {
            tokenStorage.clear();
            releaseQueue(null);
            return Promise.reject(refreshError);
        } finally {
            isRefreshing = false;
        }
    },
);

export { tokenStorage };
