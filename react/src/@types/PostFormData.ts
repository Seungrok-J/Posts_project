import {Category} from "./Category";

export interface PostFormData {
    category: {
            cateName: string;
        },
    title: string;
    content: string;
}