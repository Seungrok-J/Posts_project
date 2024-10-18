import {Category} from "./Category";
import {User} from "./User";

export interface Board {
    boardId: string;
    title: string;
    content: string;
    category: Category;
    user: User;
    updatedAt: string;
    fileName?: string;
}
