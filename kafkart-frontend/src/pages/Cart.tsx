import { Layout } from "@/components/Layout";
import { useCart } from "@/context/cart";

function Cart() {
    const { items } = useCart();

    console.log({ items });

    return <Layout>
        <div className="flex flex-col w-full h-full justify-center items-center gap-8 py-8 overflow-hidden">
            <h1>Cart</h1>
        </div>
    </Layout>
}

export default Cart;
