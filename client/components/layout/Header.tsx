import { Phone, MapPin } from "lucide-react";
import Navbar from "@/components/layout/common/Navbar";
import { Separator } from "@/components/ui/separator";
export function Header() {
    return (
        <header className="w-full">
            {/* Top Bar */}
            <div className="bg-blue-600 text-white py-2 text-sm">
                <div className="container mx-auto flex justify-between px-4">
                    <div className="flex gap-4">
                        <span className="flex items-center gap-1">
                            <Phone size={14} /> 0345 658 495
                        </span>
                        <Separator orientation="vertical" />
                        <span className="flex items-center gap-1 invisible md:visible">
                            <MapPin size={14} /> Giao hàng toàn quốc
                        </span>
                        <span className="flex items-center gap-1 invisible md:visible">
                            <MapPin size={14} /> Giao hàng toàn quốc
                        </span>
                    </div>
                    <div className="flex gap-4">
                        <span className="flex items-center gap-1">Về chúng tôi</span>
                        <Separator orientation="vertical" />
                        <span className="flex items-center gap-1 invisible md:visible">Tin tức</span>
                    </div>
                </div>
            </div>
            {/* Main Nav */}
            <Navbar />
        </header>
    );
}
