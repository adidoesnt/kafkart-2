import { Layout } from "@/components/Layout";
import { Button } from "@/components/ui/button";
import { useAuth } from "@/context/auth";
import { productApiClient } from "@/utils/apiClient";
import { publishProductView } from "@/utils/solace";
import { getStockBadge } from "@/utils/stock";
import { useCallback, useEffect, useState } from "react";
import {
	Card,
	CardContent,
	CardDescription,
	CardFooter,
	CardHeader,
	CardTitle,
} from "@/components/ui/card"
import { useCart } from "@/context/cart";

export type Product = {
	id: number;
	name: string;
	shortDescription: string;
	description: string;
	price: number;
	image: string;
	stock: number;
};

export type ProductPopupProps = {
	product: Product;
	isOpen: boolean;
	setIsOpen: (isOpen: boolean) => void;
};

export const ProductPopup = ({ product, isOpen, setIsOpen }: ProductPopupProps) => {
	const { addToCart } = useCart();
	const { user } = useAuth();

	const onClick = useCallback(() => {
		addToCart(product.id, user!.id)
		setIsOpen(false);
	}, [addToCart, product, user, setIsOpen]);

	return isOpen && (
		<div className="absolute top-0 left-0 w-full h-full bg-black/50 flex flex-col items-center justify-center">
			<Card className="bg-gray-700 text-white">
				<CardHeader className="flex flex-col items-center justify-center">
					<CardTitle>{product.name}</CardTitle>
					<CardDescription className="text-gray-300">{product.shortDescription}</CardDescription>
				</CardHeader>
				<CardContent className="flex flex-col items-center justify-center gap-2">
					<img
						className="w-80 aspect-square h-80 rounded-md"
						src={product.image}
						alt={product.name}
					/>
					<div className="flex flex-col w-full justify-center items-start gap-2">
						<p className="text-sm">{product.description}</p>
						<p className="text-sm font-semibold">Price: ${product.price}</p>
					</div>
				</CardContent>
				<CardFooter className="flex w-full items-center justify-between">
					<Button variant={"secondary"} onClick={setIsOpen.bind(null, false)}>Cancel</Button>
					<Button onClick={onClick} disabled={product.stock === 0}>Add to cart</Button>
				</CardFooter>
			</Card>
		</div>
	);
}

export const ProductCard = ({ product }: { product: Product }) => {
	const { user } = useAuth();
	const [popupIsOpen, setPopupIsOpen] = useState(false);

	const onClick = async () => {
		setPopupIsOpen(true);
		await publishProductView(user!.id, product.id);
	};

	return (
		<>
			<ProductPopup product={product} isOpen={popupIsOpen} setIsOpen={setPopupIsOpen} />
			<div
				key={product.id}
				className="flex flex-col justify-center items-center gap-2 bg-gray-600 rounded-md p-4 w-full h-full text-center"
			>
				<h2 className="text-xl font-bold">{product.name}</h2>
				<img
					className="w-80 aspect-square h-80 rounded-md"
					src={product.image}
					alt={product.name}
				/>
				<p className="text-sm">{product.shortDescription}</p>
				<p className="text-sm font-semibold">Price: ${product.price}</p>
				<p className="text-sm">{getStockBadge(product.stock)}</p>
				<br />
				<Button
					className="bg-gray-200 hover:bg-gray-400 text-gray-900"
					onClick={onClick}
				>
					View Product
				</Button>
			</div>
		</>
	);
};

function Products() {
	const [products, setProducts] = useState<Product[]>([]);

	const fetchProducts = useCallback(async () => {
		try {
			const response = await productApiClient.get("");
			const fetchedProducts = response.data;

			setProducts(fetchedProducts);
		} catch (error) {
			console.error("Error fetching products:", error);
		}
	}, [setProducts]);

	useEffect(() => {
		fetchProducts();
	}, [fetchProducts]);

	return (
		<Layout>
			<div className="flex flex-col w-full h-full justify-center items-center gap-8 py-8 overflow-hidden">
				<h1 className="text-2xl font-bold">All Products</h1>
				<div className="grid grid-flow-row grid-cols-3 px-4 gap-8 w-full h-full items-center justify-center overflow-auto">
					{products.map((product) => (
						<ProductCard key={product.id} product={product} />
					))}
				</div>
			</div>
		</Layout>
	);
}

export default Products;
