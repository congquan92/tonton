import CategoryGrid from "@/app/trang-chu/_components/CategoryGrid";
import Hero from "@/app/trang-chu/_components/Hero";
import { Header } from "@/components/layout/Header";

export default function Home() {
    return (
        <div>
            <Header />
            <Hero />
            <CategoryGrid />
        </div>
    );
}
