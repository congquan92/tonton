export type RoleName = "B2C" | "B2B" | "SALE" | "ADMIN";

export type OrderStatus =
  | "PROCESSING"
  | "CONFIRMED"
  | "SHIPPING"
  | "COMPLETED"
  | "CANCELLED";

export type PaymentMethod = "COD" | "BANK_TRANSFER" | "CARD" | "E_WALLET";

export type PostStatus = "DRAFT" | "PUBLISHED" | "ARCHIVED";

export type QuoteStatus =
  | "PENDING_CALL"
  | "PENDING_APPROVAL"
  | "APPROVED"
  | "REJECTED"
  | "CANCELLED";

export type CheckoutShippingMethod = "STANDARD" | "EXPRESS";
