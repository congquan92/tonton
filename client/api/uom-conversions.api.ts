import { createCrudApi } from "@/api/core/crud.api";
import type { UomConversionRequest } from "@/types/request";
import type { UomConversionResponse } from "@/types/response";

export const uomConversionsApi = createCrudApi<UomConversionRequest, UomConversionResponse>(
  "/uom-conversions",
);
