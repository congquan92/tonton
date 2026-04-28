import { Button } from "@/components/ui/button";
import Image from "next/image";

export default function Home() {
    return (
        <>
            <div className="bg-white">
                <Button variant="outline">Hello World</Button>
                <Image src="/logo.svg" alt="Logo" width={500} height={500} />
            </div>
        </>
    );
}
