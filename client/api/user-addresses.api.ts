import { createCrudApi } from "@/api/core/crud.api";
import type { UserAddressRequest } from "@/types/request";
import type { UserAddressResponse } from "@/types/response";

export const userAddressesApi = createCrudApi<UserAddressRequest, UserAddressResponse>(
  "/user-addresses",
);
