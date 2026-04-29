import Image from "next/image";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { MapPin, Phone, Mail, Globe, Send } from "lucide-react";

export default function Footer() {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="bg-[#0b224f] text-white pt-16 pb-6">
            <div className="container mx-auto px-4">
                {/* Main Footer Content */}
                <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-6 gap-8 mb-12">
                    {/* Cột 1: Branding */}
                    <div className="space-y-4 lg:col-span-1">
                        <div className="bg-white inline-block p-2 rounded">
                            {/* Chỗ này thay bằng logo thật của ông */}
                            <Image src="/logo.png" alt="Thành Phát Logo" width={140} height={40} className="object-contain" />
                        </div>
                        <p className="text-gray-300 text-sm leading-relaxed">Thành Phát cam kết mang đến sản phẩm chất lượng cao, giá cả cạnh tranh và dịch vụ chuyên nghiệp, đồng hành cùng sự phát triển của mọi công trình.</p>
                        <div className="flex gap-3 pt-2">
                            {/* <a href="#" className="bg-white text-[#0b224f] p-1.5 rounded-full hover:bg-blue-200 transition-colors">
                                <Facebook size={18} fill="currentColor" />
                            </a>
                            <a href="#" className="bg-white text-[#0b224f] p-1.5 rounded-full hover:bg-blue-200 transition-colors text-xs font-bold flex items-center justify-center w-[30px] h-[30px]">
                                Zalo
                            </a>
                            <a href="#" className="bg-white text-[#0b224f] p-1.5 rounded-full hover:bg-blue-200 transition-colors">
                                <Youtube size={18} fill="currentColor" />
                            </a>
                            <a href="#" className="bg-white text-[#0b224f] p-1.5 rounded-full hover:bg-blue-200 transition-colors">
                                <Linkedin size={18} fill="currentColor" />
                            </a> */}
                        </div>
                    </div>

                    {/* Cột 2: Về chúng tôi */}
                    <div>
                        <h4 className="font-bold mb-5 uppercase text-[15px]">VỀ CHÚNG TÔI</h4>
                        <ul className="space-y-3 text-sm text-gray-300">
                            {["Giới thiệu", "Tầm nhìn – Sứ mệnh", "Năng lực", "Chứng chỉ – Chứng nhận", "Tuyển dụng", "Liên hệ"].map((item) => (
                                <li key={item}>
                                    <a href="#" className="hover:text-white transition-colors">
                                        {item}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </div>

                    {/* Cột 3: Sản phẩm */}
                    <div>
                        <h4 className="font-bold mb-5 uppercase text-[15px]">SẢN PHẨM</h4>
                        <ul className="space-y-3 text-sm text-gray-300">
                            {["Tôn lợp", "Thép xây dựng", "Thép hộp – Ống thép", "Thép cuộn – Tôn cuộn", "Vật liệu xây dựng", "Thiết bị & phụ kiện"].map((item) => (
                                <li key={item}>
                                    <a href="#" className="hover:text-white transition-colors">
                                        {item}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </div>

                    {/* Cột 4: Chính sách */}
                    <div>
                        <h4 className="font-bold mb-5 uppercase text-[15px]">CHÍNH SÁCH</h4>
                        <ul className="space-y-3 text-sm text-gray-300">
                            {["Chính sách chất lượng", "Chính sách bảo hành", "Chính sách đổi trả", "Chính sách vận chuyển", "Chính sách thanh toán", "Điều khoản sử dụng"].map((item) => (
                                <li key={item}>
                                    <a href="#" className="hover:text-white transition-colors">
                                        {item}
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </div>

                    {/* Cột 5: Liên hệ */}
                    <div>
                        <h4 className="font-bold mb-5 uppercase text-[15px]">LIÊN HỆ</h4>
                        <ul className="space-y-4 text-sm text-gray-300">
                            <li className="flex gap-3 items-start">
                                <MapPin size={18} className="shrink-0 mt-0.5" />
                                <span>Trụ sở: 123 Đường số 1, KCN VSIP, Thuận An, Bình Dương</span>
                            </li>
                            <li className="flex gap-3 items-center">
                                <Phone size={18} className="shrink-0" />
                                <span>0909 123 456</span>
                            </li>
                            <li className="flex gap-3 items-center">
                                <Mail size={18} className="shrink-0" />
                                <span>info@thanhphatsteel.vn</span>
                            </li>
                            <li className="flex gap-3 items-center">
                                <Globe size={18} className="shrink-0" />
                                <span>www.thanhphatsteel.vn</span>
                            </li>
                        </ul>
                    </div>

                    {/* Cột 6: Đăng ký nhận tin */}
                    <div>
                        <h4 className="font-bold mb-5 uppercase text-[15px]">ĐĂNG KÝ NHẬN TIN</h4>
                        <p className="text-sm text-gray-300 mb-4 leading-relaxed">Nhận tin tức, báo giá và ưu đãi mới nhất từ Thành Phát.</p>
                        <div className="relative flex items-center">
                            <Input type="email" placeholder="Nhập email của bạn" className="pr-10 bg-white text-black placeholder:text-gray-500 border-none rounded-md h-10" />
                            <Button size="icon" variant="ghost" className="absolute right-0 top-0 h-10 w-10 text-[#0b224f] hover:bg-transparent hover:text-blue-700">
                                <Send size={18} />
                            </Button>
                        </div>
                    </div>
                </div>

                {/* Copyright Line */}
                <div className="border-t border-white/20 pt-6 text-center text-sm text-gray-400">© {currentYear} Thành Phát Steel. All rights reserved.</div>
            </div>
        </footer>
    );
}
