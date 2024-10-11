import {create} from 'zustand';
import { persist, createJSONStorage  } from 'zustand/middleware';

interface User {
	userId: string;
	userName: string;
	nickName: string;
	userEmail: string;
	sessionId: string;
	role: string;
}

interface UserState {
	isLoggedIn: boolean;
	user: User | null;
	setUser: (user: User | null) => void;
	logout: () => void;
}

const useUserStore = create<UserState>()(persist((set) => ({
	isLoggedIn: false,
	user: null,
	setUser: (user: User | null) => set({ user, isLoggedIn: !!user }),
	logout: () => set({ isLoggedIn: false, user: null }),
}), {
	name: 'user-storage',
	storage: createJSONStorage(() => sessionStorage),
}));

export default useUserStore;
