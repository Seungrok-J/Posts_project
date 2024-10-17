import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';

interface User {
    userId: string; // 식별자로 사용
    userName: string;
    nickName: string;
    userEmail: string;
    sessionId: string;
}

interface UserState {
    isLoggedIn: boolean;
    user: User | null;
    setUser: (user: User | null) => void;
    logout: () => void;
}

const useUserStore = create<UserState>()(
    persist((set) => ({
        isLoggedIn: false,
        user: null,
        setUser: (user: User | null) => {
            if (user) {
                set({
                    user,
                    isLoggedIn: true,
                });
            } else {
                set({
                    user: null,
                    isLoggedIn: false,
                });
            }
        },
        logout: () => set({ isLoggedIn: false, user: null }), // 로그아웃 처리
    }), {
        name: 'user-storage',
        storage: createJSONStorage(() => sessionStorage),
    })
);

export default useUserStore;
