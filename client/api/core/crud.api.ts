import { axiosInstance } from "@/lib/axios";

export interface CrudApi<TRequest, TResponse> {
  list(): Promise<TResponse[]>;
  getById(id: number): Promise<TResponse>;
  create(payload: TRequest): Promise<TResponse>;
  update(id: number, payload: TRequest): Promise<TResponse>;
  remove(id: number): Promise<void>;
}

export const createCrudApi = <TRequest, TResponse>(
  resourcePath: string,
): CrudApi<TRequest, TResponse> => ({
  async list() {
    const response = await axiosInstance.get<TResponse[]>(resourcePath);
    return response.data;
  },
  async getById(id) {
    const response = await axiosInstance.get<TResponse>(`${resourcePath}/${id}`);
    return response.data;
  },
  async create(payload) {
    const response = await axiosInstance.post<TResponse>(resourcePath, payload);
    return response.data;
  },
  async update(id, payload) {
    const response = await axiosInstance.put<TResponse>(`${resourcePath}/${id}`, payload);
    return response.data;
  },
  async remove(id) {
    await axiosInstance.delete(`${resourcePath}/${id}`);
  },
});
