import { createCrudApi } from "@/api/core/crud.api";
import type { QuoteRequest } from "@/types/request";
import type { QuoteResponse } from "@/types/response";

export const quotesApi = createCrudApi<QuoteRequest, QuoteResponse>("/quotes");
