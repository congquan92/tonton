import type { PageResponse } from "@/types/common/api.types";
import type {
  CheckoutShippingMethod,
  OrderStatus,
  PaymentMethod,
  PostStatus,
  QuoteStatus,
  RoleName,
} from "@/types/common/enums";

export interface AuthResponse {
  accessToken: string;
  accessTokenExpiresInMs: number;
}

export interface AuthUserResponse {
  id: number;
  username: string;
  fullName: string;
  email: string;
  role: RoleName;
}

export interface CartItemResponse {
  id: number;
  productId: number;
  productSku: string;
  productName: string;
  productImageUrl?: string;
  uomId: number;
  uomSymbol?: string;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

export interface CartResponse {
  id: number | null;
  userId: number;
  totalQuantity: number;
  subtotal: number;
  vatAmount: number;
  shippingFee: number;
  discountAmount: number;
  totalAmount: number;
  items: CartItemResponse[];
}

export interface CategoryResponse {
  id: number;
  parentId?: number;
  name: string;
  slug: string;
  isActive: boolean;
}

export interface CheckoutResponse {
  orderId: number;
  status: OrderStatus;
  paymentMethod: PaymentMethod;
  shippingMethod: CheckoutShippingMethod;
  subtotal: number;
  vatAmount: number;
  shippingFee: number;
  totalAmount: number;
  itemCount: number;
}

export interface LeadResponse {
  success: boolean;
  message: string;
}

export interface OrderItemResponse {
  id: number;
  orderId: number;
  productId: number;
  uomId: number;
  quantity: number;
  price: number;
}

export interface OrderResponse {
  id: number;
  quoteId?: number;
  userId: number;
  userFullName?: string;
  totalAmount: number;
  shippingFee: number;
  shippingAddressId?: number;
  shippingAddressSnapshot: string;
  status: OrderStatus;
  paymentMethod: PaymentMethod;
  createdAt: string;
  updatedAt: string;
}

export interface PostCategoryResponse {
  id: number;
  name: string;
  slug: string;
  isActive: boolean;
}

export interface PostResponse {
  id: number;
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  content: string;
  categoryId?: number;
  categoryName?: string;
  authorId?: number;
  authorName?: string;
  status: PostStatus;
  viewCount?: number;
  publishedAt?: string;
  createdAt: string;
  updatedAt: string;
}

export interface PriceTierResponse {
  id: number;
  productId: number;
  roleId: number;
  minQty: number;
  price: number;
}

export interface ProductImageResponse {
  id: number;
  productId: number;
  imageUrl: string;
  isPrimary?: boolean;
  sortOrder?: number;
}

export interface ProductResponse {
  id: number;
  sku: string;
  categoryId?: number;
  categoryName?: string;
  name: string;
  baseUomId: number;
  baseUomName?: string;
  quantity?: number;
  attributes: Record<string, unknown>;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface ProjectResponse {
  id: number;
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
  createdAt: string;
  updatedAt: string;
}

export interface QuoteResponse {
  id: number;
  userId: number;
  userFullName?: string;
  status: QuoteStatus;
  note?: string;
  createdAt: string;
  updatedAt: string;
}

export interface QuoteItemResponse {
  id: number;
  quoteId: number;
  productId: number;
  uomId: number;
  quantity: number;
  quotedPrice?: number;
}

export interface RoleResponse {
  id: number;
  name: RoleName;
  description?: string;
}

export interface ServiceFeatureResponse {
  id: number;
  name: string;
  slug: string;
  iconUrl?: string;
  shortDescription?: string;
  content?: string;
  sortOrder?: number;
  isActive?: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface UomResponse {
  id: number;
  name: string;
  symbol?: string;
}

export interface UomConversionResponse {
  id: number;
  productId: number;
  fromUomId: number;
  toUomId: number;
  conversionRate: number;
}

export interface UserAddressResponse {
  id: number;
  userId: number;
  title?: string;
  receiverName: string;
  receiverPhone: string;
  address: string;
  isDefault?: boolean;
}

export interface UserResponse {
  id: number;
  phoneNumber: string;
  fullName: string;
  dateOfBirth?: string;
  email: string;
  username: string;
  roleId: number;
  roleName: RoleName;
  tokenVersion: number;
  createdAt: string;
  updatedAt: string;
}

export interface ImageUploadResponse {
  url: string;
  secureUrl: string;
  publicId: string;
  format: string;
  width: number;
  height: number;
  bytes: number;
}

export interface PublicPriceTierResponse {
  minQty: number;
  price: number;
  role: string;
}

export interface HomeStatsResponse {
  activeProducts: number;
  featuredProjects: number;
  publishedPosts: number;
  activeServices: number;
}

export interface PublicProductCardResponse {
  id: number;
  sku: string;
  name: string;
  categoryId?: number;
  categoryName?: string;
  baseUomSymbol?: string;
  primaryImageUrl?: string;
  displayPrice: number;
}

export interface PublicProductDetailResponse {
  id: number;
  sku: string;
  name: string;
  categoryId?: number;
  categoryName?: string;
  baseUomId?: number;
  baseUomName?: string;
  baseUomSymbol?: string;
  quantity?: number;
  attributes: Record<string, unknown>;
  primaryImageUrl?: string;
  imageUrls: string[];
  priceTiers: PublicPriceTierResponse[];
  displayPrice: number;
  createdAt: string;
  updatedAt: string;
}

export interface PublicPostCardResponse {
  id: number;
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  categoryId?: number;
  categoryName?: string;
  viewCount?: number;
  publishedAt?: string;
}

export interface PublicPostDetailResponse {
  id: number;
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  content: string;
  categoryId?: number;
  categoryName?: string;
  authorName?: string;
  viewCount?: number;
  publishedAt?: string;
  createdAt: string;
  updatedAt: string;
  relatedPosts: PublicPostCardResponse[];
}

export interface PublicProjectCardResponse {
  id: number;
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  category?: string;
  location?: string;
  completedAt?: string;
}

export interface PublicProjectDetailResponse {
  id: number;
  title: string;
  slug: string;
  thumbnailUrl?: string;
  summary?: string;
  content?: string;
  category?: string;
  location?: string;
  completedAt?: string;
  createdAt: string;
  updatedAt: string;
}

export interface PublicServiceFeatureResponse {
  id: number;
  name: string;
  slug: string;
  iconUrl?: string;
  shortDescription?: string;
  content?: string;
  sortOrder?: number;
}

export interface PublicHomeResponse {
  stats: HomeStatsResponse;
  featuredProducts: PublicProductCardResponse[];
  featuredProjects: PublicProjectCardResponse[];
  latestPosts: PublicPostCardResponse[];
  services: PublicServiceFeatureResponse[];
}

export type PublicProductsPageResponse = PageResponse<PublicProductCardResponse>;
export type PublicPostsPageResponse = PageResponse<PublicPostCardResponse>;
export type PublicProjectsPageResponse = PageResponse<PublicProjectCardResponse>;
