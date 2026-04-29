import Image from "next/image";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { ShieldCheck, Truck, HeadphonesIcon, FileCheck, Handshake, ChevronRight, Clock, Tag, Building2, Users } from "lucide-react";

// --- DATA MẪU (Tách riêng cho Clean Code) ---
const FEATURES = [
    { icon: ShieldCheck, title: "CHẤT LƯỢNG ĐẢM BẢO", desc: "Sản phẩm chính hãng, đạt chuẩn quốc tế." },
    // { icon: BadgeDollar, title: "GIÁ CẢ CẠNH TRANH", desc: "Giá tốt nhờ hệ thống nhập hàng và phân phối tối ưu." },
    { icon: Truck, title: "GIAO HÀNG TOÀN QUỐC", desc: "Giao nhanh chóng, đúng tiến độ, hỗ trợ tận nơi." },
    { icon: HeadphonesIcon, title: "TƯ VẤN CHUYÊN NGHIỆP", desc: "Đội ngũ giàu kinh nghiệm, hỗ trợ 24/7." },
    { icon: FileCheck, title: "ĐA DẠNG CHỦNG LOẠI", desc: "Đáp ứng mọi nhu cầu từ dân dụng đến dự án lớn." },
    { icon: Handshake, title: "HỢP TÁC BỀN VỮNG", desc: "Đồng hành lâu dài, phát triển cùng đối tác." },
];

const PRODUCTS = [
    { title: "TÔN LỢP", img: "/ton.png" },
    { title: "THÉP XÂY DỰNG", img: "/thep.png" },
    { title: "THÉP HỘP - ỐNG THÉP", img: "/thep-hop.png" },
    { title: "THÉP CUỘN - TÔN CUỘN", img: "/thep-cuon.png" },
    { title: "VẬT LIỆU XÂY DỰNG", img: "/vlxd.png" },
    { title: "THIẾT BỊ & PHỤ KIỆN", img: "/phu-kien.png" },
];

const NEWS = [
    { date: "20/05/2024", title: "Giá thép xây dựng hôm nay 20/05/2024: Cập nhật mới nhất", img: "/news1.jpg" },
    { date: "15/05/2024", title: "Hướng dẫn chọn tôn lợp mái chất lượng, bền đẹp", img: "/news2.jpg" },
    { date: "10/05/2024", title: "Xu hướng vật liệu xây dựng bền vững năm 2024", img: "/news3.jpg" },
];

// --- COMPONENTS ---

export default function MainContent() {
    return (
        <div className="w-full bg-white flex flex-col gap-16 py-12">
            {/* 1. VÌ SAO CHỌN THÀNH PHÁT */}
            <section className="container mx-auto px-4">
                <div className="text-center mb-8">
                    <h2 className="text-2xl font-black text-[#1a3a71] uppercase">VÌ SAO CHỌN THÀNH PHÁT?</h2>
                    <div className="w-16 h-1 bg-blue-600 mx-auto mt-3"></div>
                </div>
                <div className="grid grid-cols-2 lg:grid-cols-6 gap-4">
                    {FEATURES.map((feat, idx) => (
                        <Card key={idx} className="border border-gray-100 hover:shadow-lg transition-all text-center">
                            <CardContent className="p-6 flex flex-col items-center justify-start h-full gap-3">
                                <div className="w-12 h-12 rounded-full bg-blue-50 flex items-center justify-center text-blue-700">
                                    <feat.icon size={24} />
                                </div>
                                <h3 className="font-bold text-[13px] text-[#1a3a71] uppercase">{feat.title}</h3>
                                <p className="text-[12px] text-gray-500 leading-tight">{feat.desc}</p>
                            </CardContent>
                        </Card>
                    ))}
                </div>
            </section>

            {/* 2. SẢN PHẨM ĐA DẠNG */}
            <section className="container mx-auto px-4">
                <div className="flex flex-col lg:flex-row gap-8 items-center">
                    {/* Cột chữ bên trái */}
                    <div className="lg:w-1/4">
                        <h2 className="text-xl font-black text-[#1a3a71] uppercase mb-4">SẢN PHẨM ĐA DẠNG</h2>
                        <p className="text-sm text-gray-600 mb-6">Cung cấp đầy đủ các chủng loại tôn, thép và vật liệu xây dựng cho mọi công trình.</p>
                        <Button className="bg-[#1a3a71] hover:bg-blue-800 w-fit">
                            XEM TẤT CẢ SẢN PHẨM <ChevronRight className="w-4 h-4 ml-2" />
                        </Button>
                    </div>
                    {/* Lưới sản phẩm bên phải */}
                    <div className="lg:w-3/4 w-full">
                        <div className="flex gap-4 overflow-x-auto pb-4 snap-x hide-scrollbar">
                            {PRODUCTS.map((prod, idx) => (
                                <div key={idx} className="min-w-[160px] md:min-w-[200px] snap-start group cursor-pointer border rounded-lg overflow-hidden flex flex-col hover:border-blue-500 transition-all">
                                    <div className="h-32 bg-gray-50 p-2">
                                        {/* Dùng div cover tạm, ông bỏ Image src vào là đẹp */}
                                        <div className="w-full h-full bg-slate-200 rounded object-cover flex items-center justify-center text-xs text-gray-400">Ảnh {prod.title}</div>
                                    </div>
                                    <div className="p-3 bg-white flex justify-between items-center flex-1">
                                        <span className="font-bold text-[11px] text-[#1a3a71]">{prod.title}</span>
                                        <ChevronRight className="w-4 h-4 text-gray-400 group-hover:text-blue-600" />
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </section>

            {/* 3. BANNER BÁO GIÁ */}
            <section className="container mx-auto px-4">
                <div className="rounded-2xl overflow-hidden bg-[#0b224f] flex flex-col md:flex-row relative">
                    <div className="md:w-1/3 h-48 md:h-auto bg-gray-400 relative clip-path-banner">
                        {/* Ảnh công trường bên trái */}
                        <div className="absolute inset-0 bg-slate-600 flex items-center justify-center text-white text-sm">Ảnh Công Trường</div>
                    </div>
                    <div className="md:w-2/3 p-8 flex flex-col justify-center text-white z-10">
                        <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-6">
                            <div>
                                <h3 className="text-2xl font-black mb-2">BÁO GIÁ NHANH – NHẬN HÀNG NGAY</h3>
                                <p className="text-sm text-blue-200 mb-6">Gửi yêu cầu báo giá, chúng tôi sẽ phản hồi trong vòng 15 phút!</p>
                                <div className="flex flex-wrap gap-6 text-sm">
                                    <span className="flex items-center gap-2">
                                        <Clock size={16} className="text-blue-400" /> Báo giá nhanh chóng
                                    </span>
                                    <span className="flex items-center gap-2">
                                        <Tag size={16} className="text-blue-400" /> Giá cạnh tranh nhất
                                    </span>
                                    <span className="flex items-center gap-2">
                                        <HeadphonesIcon size={16} className="text-blue-400" /> Hỗ trợ tận tâm 24/7
                                    </span>
                                </div>
                            </div>
                            <Button className="bg-white text-[#0b224f] hover:bg-gray-100 font-bold px-8 whitespace-nowrap">
                                YÊU CẦU BÁO GIÁ <FileCheck className="w-4 h-4 ml-2" />
                            </Button>
                        </div>
                    </div>
                </div>
            </section>

            {/* 4. TIN TỨC - KIẾN THỨC (Phần dự án làm slider giống cái trước tôi đưa r nha, tôi pass sang Tin tức luôn) */}
            <section className="container mx-auto px-4">
                <div className="flex justify-between items-end mb-6">
                    <h2 className="text-xl font-black text-[#1a3a71] uppercase tracking-wide">TIN TỨC – KIẾN THỨC</h2>
                    <a href="#" className="text-sm text-blue-600 hover:underline font-medium flex items-center">
                        XEM TẤT CẢ <ChevronRight className="w-4 h-4" />
                    </a>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {NEWS.map((item, idx) => (
                        <div key={idx} className="flex gap-4 group cursor-pointer border p-3 rounded-xl hover:shadow-md transition-all bg-white">
                            <div className="w-1/3 rounded-lg overflow-hidden bg-gray-200 shrink-0">
                                <div className="w-full h-full flex items-center justify-center text-xs text-gray-400 min-h-[80px]">Ảnh News</div>
                            </div>
                            <div className="flex flex-col justify-between py-1">
                                <span className="text-[11px] text-gray-400">{item.date}</span>
                                <h3 className="font-bold text-[13px] text-[#1a3a71] group-hover:text-blue-600 line-clamp-2 leading-snug">{item.title}</h3>
                                <span className="text-[12px] text-blue-600 font-medium mt-2">Xem thêm →</span>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
        </div>
    );
}


