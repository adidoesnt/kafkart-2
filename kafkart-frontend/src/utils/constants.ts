export const {
	VITE_USER_API_BASE_URL: USER_API_BASE_URL = "http://localhost:3001/users",
	VITE_PRODUCT_API_BASE_URL: PRODUCT_API_BASE_URL = "http://localhost:3001/products",
	VITE_SOLACE_HOST: SOLACE_HOST = "ws://localhost:8008",
	VITE_SOLACE_VPN_NAME: SOLACE_VPN_NAME = "default",
	VITE_SOLACE_USERNAME: SOLACE_USERNAME = "frontend_user",
	VITE_SOLACE_PASSWORD: SOLACE_PASSWORD = "password",
	VITE_SOLACE_TOPIC: SOLACE_TOPIC = "product/events",
} = import.meta.env;
