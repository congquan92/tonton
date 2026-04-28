import { Button } from "@/components/ui/button";
import { NavigationMenu, NavigationMenuItem, NavigationMenuLink, NavigationMenuList, navigationMenuTriggerStyle } from "@/components/ui/navigation-menu";
import { Separator } from "@/components/ui/separator";
import { FileText, Search, ShoppingCart } from "lucide-react";
import Image from "next/image";

export default function Navbar() {
    return (
        <>
            {/* Main Nav */}
            <nav className="container mx-auto flex items-center justify-between py-4">
                <Image src="/logo.svg" alt="Thành Phát" width={200} height={200} />

                <NavigationMenu className="hidden lg:flex">
                    <NavigationMenuList className="gap-4 cursor-pointer">
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        <NavigationMenuItem>
                            <NavigationMenuLink className={navigationMenuTriggerStyle()}>TRANG CHỦ</NavigationMenuLink>
                        </NavigationMenuItem>
                        {/* Thêm các item khác tương tự */}
                    </NavigationMenuList>
                </NavigationMenu>

                <div className="flex items-center gap-3">
                    <Button variant="ghost" size="icon">
                        <Search />
                    </Button>
                    <Separator orientation="vertical" />
                    <Button variant="ghost" size="icon" className="relative">
                        <ShoppingCart />
                        <span className="absolute top-0 right-0 bg-primary text-white text-[10px] rounded-full px-1">0</span>
                    </Button>
                    <Button className="bg-blue-600 hover:bg-blue-800 py-3 px-5">
                        <FileText className="mr-2 h-4 w-4" /> BÁO GIÁ
                    </Button>
                </div>
            </nav>
        </>
    );
}
