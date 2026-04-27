import type {
  CheckoutShippingMethod,
  OrderStatus,
  PaymentMethod,
  PostStatus,
  QuoteStatus,
  RoleName,
} from "@/types/common/enums";

export interface AuthLoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface AuthRegisterRequest {
  phoneNumber: string;
  fullName: string;
  dateOfBirth?: string;
  email: string;
  username: string;
  password: string;
}

export interface CartItemUpsertRequest {
  productId: number;
  uomId?: number;
  quantity: number;
}

export interface CartItemQuantityRequest {
  quantity: number;
}

export interface CheckoutRequest {
  shippingAddressId?: number;
  shippingAddressSnapshot?: string;
  shippingMethod: CheckoutShippingMethod;
  paymentMethod: PaymentMethod;
}

export interface CategoryRequest {
  parentId?: number | null;
  name: string;
  slug: string;
  isActive?: boolean;
}

export interface ContactMessageRequest {
  fullName: string;
  phoneNumber?: string;
  email?: string;
  sourcePage?: string;
  message: string;
}

export interface NewsletterSubscriptionRequest {
  email: string;
}

export interface OrderRequest {
  quoteId?: number;
  userId: number;
  totalAmount: number;
  shippingFee?: number;
  shippingAddressId?: number;
  shippingAddressSnapshot: string;
  status?: OrderStatus;
  paymentMethod?: PaymentMethod;
}

export interface OrderItemRequest {
  orderId: number;
  productId: number;
  uomId: number;
  quantity: number;
  price: number;
}

export interface PostCategoryRequest {
  name: string;
  slug: string;
  isActive?: boolean;
}

export interface PostRequest {
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  content: string;
  categoryId?: number;
  authorId?: number;
  status?: PostStatus;
  viewCount?: number;
  publishedAt?: string;
}

export interface PriceTierRequest {
  productId: number;
  roleId: number;
  minQty: number;
  price: number;
}

export interface ProductImageRequest {
  productId: number;
  imageUrl: string;
  isPrimary?: boolean;
  sortOrder?: number;
}

export interface ProductRequest {
  sku: string;
  categoryId?: number;
  name: string;
  baseUomId: number;
  quantity?: number;
  attributes: Record<string, unknown>;
  isActive?: boolean;
}

export interface ProjectRequest {
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  content?: string;
  category?: string;
  location?: string;
  completedAt?: string;
  featured?: boolean;
  isActive?: boolean;
}

export interface QuoteRequest {
  userId: number;
  status?: QuoteStatus;
  note?: string;
}

export interface QuoteItemRequest {
  quoteId: number;
  productId: number;
  uomId: number;
  quantity: number;
  quotedPrice?: number;
}

export interface RoleRequest {
  name: RoleName;
  description?: string;
}

export interface ServiceFeatureRequest {
  name: string;
  slug: string;
  iconUrl?: string;
  shortDescription?: string;
  content?: string;
  sortOrder?: number;
  isActive?: boolean;
}

export interface UomRequest {
  name: string;
  symbol?: string;
}

export interface UomConversionRequest {
  productId: number;
  fromUomId: number;
  toUomId: number;
  conversionRate: number;
}

export interface UserRequest {
  phoneNumber: string;
  fullName: string;
  dateOfBirth?: string;
  email: string;
  username: string;
  password: string;
  roleId: number;
  tokenVersion?: number;
}

export interface UserAddressRequest {
  userId: number;
  title?: string;
  receiverName: string;
  receiverPhone: string;
  address: string;
  isDefault?: boolean;
}

export interface PublicProductsQuery {
  page?: number;
  size?: number;
  categoryId?: number;
  keyword?: string;
  sort?: "newest" | "oldest" | "name_asc" | "name_desc" | "price_asc" | "price_desc";
}

export interface PublicPostsQuery {
  page?: number;
  size?: number;
  categoryId?: number;
  keyword?: string;
}

export interface PublicProjectsQuery {
  page?: number;
  size?: number;
  category?: string;
  keyword?: string;
}
