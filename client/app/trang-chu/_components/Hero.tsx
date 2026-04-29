import Image from "next/image";
import { Button } from "@/components/ui/button";
import { ShieldCheck, Tag, Truck, Headphones, Award, Users, Building2, PhoneCall, FileText } from "lucide-react"; // Import các icon cần thiết

export default function Hero() {
    return (
        <section className="relative bg-slate-50 overflow-hidden min-h-[600px] flex flex-col justify-center">
            <div className="container mx-auto px-4 z-10 py-12 lg:py-20">
                <div className="lg:w-1/2">
                    <h1 className="text-blue-800 font-bold mb-4 text-2xl tracking-wide uppercase">THÀNH PHÁT - ĐỐI TÁC TIN CẬY</h1>
                    <h1 className="text-4xl lg:text-6xl font-black text-[#1a3a71] leading-[1.1] mb-6">
                        CUNG CẤP TÔN THÉP <br />
                        <span className="text-blue-600">& VẬT LIỆU</span> XÂY DỰNG
                    </h1>
                    <p className="text-gray-600 mb-8 max-w-[550px] text-lg leading-relaxed">Chúng tôi cung cấp đa dạng các sản phẩm tôn, thép, vật liệu xây dựng chất lượng cao với giá cả cạnh tranh và dịch vụ chuyên nghiệp.</p>

                    {/* 1. Mấy cái cục icon nhỏ (Features) */}
                    <div className="grid grid-cols-2 gap-y-4 gap-x-2 mb-10">
                        {[
                            { icon: ShieldCheck, text: "Sản phẩm chất lượng" },
                            { icon: Tag, text: "Giá cả cạnh tranh" },
                            { icon: Truck, text: "Giao hàng toàn quốc" },
                            { icon: Headphones, text: "Hỗ trợ tư vấn 24/7" },
                        ].map((item, index) => (
                            <div key={index} className="flex items-center gap-3 group">
                                <div className="p-2 bg-white rounded-lg shadow-sm border group-hover:bg-blue-50 transition-colors">
                                    <item.icon className="w-5 h-5 text-blue-700" />
                                </div>
                                <span className="text-sm font-semibold text-slate-700 leading-tight">{item.text}</span>
                            </div>
                        ))}
                    </div>

                    <div className="flex flex-wrap gap-4 mb-16">
                        <Button size="lg" className="bg-blue-700 hover:bg-blue-800 px-8 py-7 text-base font-bold shadow-lg shadow-blue-200">
                            XEM SẢN PHẨM <span className="ml-2">→</span>
                        </Button>
                        <Button size="lg" variant="outline" className="border-2 border-blue-700 text-blue-700 px-8 py-7 text-base font-bold italic bg-white shadow-sm">
                            <FileText className="w-5 h-5 mr-2" /> NHẬN BÁO GIÁ
                        </Button>
                    </div>
                </div>
            </div>

            {/* Phần ảnh */}
            <div className="hidden lg:block absolute top-0 right-0 w-1/2 h-full">
                <div className="absolute inset-0 bg-blue-700 z-0 translate-x-1/4 -skew-x-12" />
                <div className="relative w-full h-full z-10 flex items-center justify-end">
                    <div className="relative w-[110%] h-[80%] -mr-16">
                        <Image src="/trangchu/steel.png" alt="Steel Products" fill priority className="object-contain object-right" sizes="50vw" />
                    </div>
                </div>
            </div>

            {/* Thanh Stats màu trắng (Floating Bar) */}
            <div className="container mx-auto px-4 z-20 -mt-12 mb-8 lg:absolute lg:bottom-3 lg:left-1/2 lg:-translate-x-1/2 lg:mb-0 ">
                <div className="bg-white rounded-2xl shadow-[0_10px_40px_rgba(0,0,0,0.08)] border p-6 lg:p-8 grid grid-cols-2 lg:grid-cols-4 gap-8 items-center">
                    {[
                        { icon: Award, val: "10+", desc: "Năm kinh nghiệm" },
                        { icon: Users, val: "1000+", desc: "Khách hàng tin tưởng" },
                        { icon: Building2, val: "2000+", desc: "Dự án đã cung cấp" },
                        { icon: PhoneCall, val: "Hotline", desc: "0909 123 456", isPhone: true },
                    ].map((stat, i, arr) => (
                        <div key={i} className={`flex items-center gap-4 pl-4 first:pl-0 ${i !== arr.length - 1 ? "border-r border-blue-500" : ""}`}>
                            <div className="hidden sm:block">
                                <stat.icon className="w-10 h-10 text-blue-600 opacity-80" />
                            </div>
                            <div>
                                <div className={`text-xl lg:text-2xl font-black ${stat.isPhone ? "text-blue-700" : "text-[#1a3a71]"}`}>{stat.val}</div>
                                <div className="text-xs lg:text-sm text-gray-500 font-medium">{stat.desc}</div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
}
