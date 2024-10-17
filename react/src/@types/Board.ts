import * as timers from "node:timers";

export interface Board {
    isDeleted: boolean;
    boardId: number;
    count: number;
    createdAt: string
    deletedAt: string | null;
    updatedAt: string | null;
    content: string;
    fileName: string | null;
    filePath: string | null;
    title: string;
    category: {
        cateId: number;
        cateName: string;
    };
    user:{
        nickName: string;
    }
}

// export interface Board {
//     title: string;
//     content: string;
//     category: {
//         cateId: number;
//     };
//     fileName?: string;
//     filePath?: string;
// }