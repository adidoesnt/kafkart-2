import { navigation } from "@/config.json";
import { Link } from "react-router";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { useAuth } from "@/context/auth";
import { emailToInitials } from "@/utils/avatar";
import { useCallback, useMemo } from "react";
import {
	HoverCard,
	HoverCardContent,
	HoverCardTrigger,
} from "@/components/ui/hover-card";
import { Button } from "./ui/button";
import { useNavigate } from "react-router";

export const Header = () => {
	const { user, logout } = useAuth();
	const profileImageUrl = useMemo(() => user!.profileImageUrl, [user]);
	const initials = useMemo(() => emailToInitials(user!.email), [user]);
	const navigate = useNavigate();

	const onClick = useCallback(() => {
		logout();
		navigate("/");
	}, [logout, navigate]);

	return (
		<div className="flex w-full h-fit justify-between items-center bg-gray-800 p-4 rounded-lg gap-8">
			<h1 className="text-2xl font-bold">
				<Link to="/">KafkaRT</Link>
			</h1>
			<div className="flex w-full justify-end items-center gap-8">
				{navigation.map(({ name, path }) => (
					<Link className="hover:text-gray-400" key={name} to={path}>
						{name}
					</Link>
				))}
			</div>
			<div className="flex w-fit justify-end items-center gap-4">
				<HoverCard>
					<HoverCardTrigger>
						<Avatar>
							<AvatarImage src={profileImageUrl} />
							<AvatarFallback className="bg-gray-700 text-white">{initials}</AvatarFallback>
						</Avatar>
					</HoverCardTrigger>
					<HoverCardContent>
						<div className="flex flex-col w-full justify-center items-center gap-2">
							<h2 className="text-xl font-bold">
								{user!.username}
							</h2>
							<p className="text-sm">{user!.email}</p>
							<br />
							<Button onClick={onClick}>Logout</Button>
						</div>
					</HoverCardContent>
				</HoverCard>
			</div>
		</div>
	);
};
