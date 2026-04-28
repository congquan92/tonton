import { axiosInstance } from "@/lib/axios";
import type { ImageUploadResponse } from "@/types/response";

export const uploadsApi = {
    async uploadImage(file: File, folder?: string): Promise<ImageUploadResponse> {
        const formData = new FormData();
        formData.append("file", file);

        if (folder) {
            formData.append("folder", folder);
        }

        const response = await axiosInstance.post<ImageUploadResponse>("/uploads/images", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });

        return response.data;
    },
};
