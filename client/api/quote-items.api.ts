import { createCrudApi } from "@/api/core/crud.api";
import type { QuoteItemRequest } from "@/types/request";
import type { QuoteItemResponse } from "@/types/response";

export const quoteItemsApi = createCrudApi<QuoteItemRequest, QuoteItemResponse>("/quote-items");
