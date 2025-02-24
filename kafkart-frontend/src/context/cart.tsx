/* eslint-disable react-refresh/only-export-components */

import { 
    useContext, 
    ReactNode, 
    useState, 
    useCallback, 
    createContext 
} from "react";

type OrderItem = {
    productId: number;
    quantity: number;
}

type CartContextType = {
    items: OrderItem[];
    addToCart: (productId: number, quantity: number) => void;
    removeFromCart: (productId: number) => void;
    clearCart: () => void;
}

const CartContext = createContext<CartContextType | null>(null);

export const useCart = () => {
    const context = useContext(CartContext);

    if (!context) {
        throw new Error("useCart must be used within a CartProvider");
    }

    return context;
};

export const CartProvider = ({ children }: { children: ReactNode }) => {
    const [items, setItems] = useState<OrderItem[]>([]);

    const addToCart = useCallback((productId: number, quantity: number) => {
        const newCart = [...items, { productId, quantity }];
        setItems(newCart);
    }, [items]);

    const removeFromCart = useCallback((productId: number) => {
        const newCart = items.filter((item) => item.productId !== productId);
        setItems(newCart);
    }, [items]);

    const clearCart = useCallback(() => {
        setItems([]);
    }, []);

    const cartContext = {
        items: items,
        addToCart,
        removeFromCart,
        clearCart,
    };

    return (
        <CartContext.Provider value={cartContext}>
            {children}
        </CartContext.Provider>
    );
};
