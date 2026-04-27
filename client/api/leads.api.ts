import { axiosInstance } from "@/lib/axios";
import type {
  ContactMessageRequest,
  NewsletterSubscriptionRequest,
} from "@/types/request";
import type { LeadResponse } from "@/types/response";

export const leadsApi = {
  async subscribeNewsletter(payload: NewsletterSubscriptionRequest): Promise<LeadResponse> {
    const response = await axiosInstance.post<LeadResponse>(
      "/public/newsletter-subscriptions",
      payload,
    );
    return response.data;
  },

  async createContactMessage(payload: ContactMessageRequest): Promise<LeadResponse> {
    const response = await axiosInstance.post<LeadResponse>("/public/contact-messages", payload);
    return response.data;
  },
};
