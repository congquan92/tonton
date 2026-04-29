import CategoryGrid from "@/app/trang-chu/_components/CategoryGrid";
import Hero from "@/app/trang-chu/_components/Hero";
import MainContent from "@/app/trang-chu/_components/MainContent";
import Footer from "@/components/layout/Footer";
import { Header } from "@/components/layout/Header";

export default function Home() {
    return (
        <div>
            <Header />
            <Hero />
            <CategoryGrid />
            <MainContent />
            <Footer />
        </div>
    );
}
