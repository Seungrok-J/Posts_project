import {create} from 'zustand';

interface BoardStore {
    selectedBoardId: string | null;
    setSelectedBoardId: (id: string) => void;
}

const useBoardStore =  create<BoardStore>((set) => ({
    selectedBoardId: null,
    setSelectedBoardId: (id:string) => set({selectedBoardId: id}),

}));
export default useBoardStore;
