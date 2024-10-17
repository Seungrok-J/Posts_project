import * as timers from "node:timers";

export interface Board {
    isDeleted: boolean;
    boardId: string;
    count: number;
    createdAt: string
    deletedAt: string | null;
    updatedAt: string | null;
    content: string;
    fileName: string | null;
    filePath: string | null;
    title: string;
    category: {
        cateId: string;
        cateName: string;
    };
    user:{
        nickName: string;
    }
}
