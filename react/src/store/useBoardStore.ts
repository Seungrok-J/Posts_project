import {create} from 'zustand';

interface BoardStore {
    selectedBoardId: number | null;
    setSelectedBoardId: (id: number) => void;
}

const useBoardStore =  create<BoardStore>((set) => ({
    selectedBoardId: null,
    setSelectedBoardId: (id:number) => set({selectedBoardId: id}),

}));
export default useBoardStore;
