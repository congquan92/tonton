import CategoryGrid from "@/app/trangchu/_components/CategoryGrid";
import Hero from "@/app/trangchu/_components/Hero";
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
