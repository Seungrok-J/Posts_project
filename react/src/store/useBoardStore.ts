import {create} from 'zustand';

interface BoardStore {
    selectedBoardId: number | null; // 선택된 게시판 ID는 숫자 또는 null
    setSelectedBoardId: (id: number) => void; // 매개변수 id는 숫자 타입
}

const useBoardStore =  create<BoardStore>((set) => ({
    selectedBoardId: null,
    setSelectedBoardId: (id:number) => set({selectedBoardId: id}),

}));
export default useBoardStore;
