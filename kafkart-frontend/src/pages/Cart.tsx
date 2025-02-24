import { Layout } from "@/components/Layout";
import { useCart } from "@/context/cart";
import { productApiClient } from "@/utils/apiClient";
import { useCallback, useEffect, useMemo, useState } from "react";
import { Product } from "./Products";
import { Button } from "@/components/ui/button";
import { publishCheckout } from "@/utils/solace";
import { useAuth } from "@/context/auth";

type ProductWithQuantity = Product & { quantity: number };

function Cart() {
const { user } = useAuth();
  const { items, getTotalPrice, checkout } = useCart();
  const [products, setProducts] = useState<Product[]>([]);

  const noItems = useMemo(() => items.length === 0, [items]);
  const totalPrice = useMemo(() => getTotalPrice(), [getTotalPrice]);

  const getProductById = useCallback(async (id: number) => {
    try {
      const response = await productApiClient.get(`/${id}`);
      return response.data;
    } catch (error) {
      console.error("Error fetching product:", error);
    }
  }, []);

  useEffect(() => {
    const fetchProducts = async () => {
      const fetchedProducts = await Promise.all(
        items.map(async (item) => {
          const product = await getProductById(item.productId);
          return { ...product, quantity: item.quantity };
        })
      );
      setProducts(fetchedProducts);
    };

    fetchProducts();
  }, [items, setProducts, getProductById]);

  const onClick = useCallback(() => {
    checkout();

    const productIdsAndQuantities = (products as ProductWithQuantity[]).map((product) => ({
      productId: product.id,
      quantity: product.quantity,
    }));
    publishCheckout(user!.id, productIdsAndQuantities, totalPrice);
  }, [checkout, products, totalPrice, user]);

  return (
    <Layout>
      <div className="flex w-full h-full justify-center items-center gap-8 py-8 overflow-hidden">
        {noItems ? (
          <h1 className="text-xl">Your cart is empty.</h1>
        ) : (
          <div className="flex flex-col w-[50dvw] h-[75dvh] rounded-md justify-start items-center gap-8 p-8 overflow-auto bg-gray-300">
            <h1 className="text-2xl font-bold text-gray-700">Cart</h1>
            {products.map((product) => (
              <div
                key={product.id}
                className="flex flex-col justify-center items-start gap-2 bg-gray-400 text-gray-700 rounded-md p-4 w-full text-center"
              >
                <h2 className="text-lg font-bold">{product.name}</h2>
                <p>Quantity: {(product as ProductWithQuantity).quantity}</p>
              </div>
            ))}
            <div className="flex w-full justify-between items-center gap-2 text-center text-xl">
                <div className="flex justify-center items-center gap-2 bg-gray-600 rounded-md p-4 w-full text-center text-xl">
                    <h2 className="text-xl font-bold">Total:</h2>
                    <p>${totalPrice.toFixed(2)}</p>
                </div>
                <Button className="text-xl h-full" onClick={onClick}>
                    Checkout
                </Button>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}

export default Cart;
