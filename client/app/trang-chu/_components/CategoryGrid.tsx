import Image from "next/image";

// components/home/CategoryGrid.tsx
const categories = [
    { name: "TÔN LỢP", img: "/ton.png" },
    { name: "THÉP XÂY DỰNG", img: "/thep.png" },
    { name: "THÉP HỘP - ỐNG THÉP", img: "/thep-hop.png" },
    // ... copy thêm data vào
];

export default function CategoryGrid() {
    return (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4 py-10">
            {categories.map((cat, i) => (
                <div key={i} className="group border rounded-lg p-4 hover:shadow-xl transition cursor-pointer bg-white text-center">
                    <div className="h-32 mb-4 overflow-hidden">
                        <Image src={cat.img} alt={cat.name} className="w-full h-full object-contain group-hover:scale-110 transition" width={128} height={128} />
                    </div>
                    <h3 className="font-bold text-sm text-[#1a3a71] uppercase">{cat.name}</h3>
                </div>
            ))}
        </div>
    );
}
