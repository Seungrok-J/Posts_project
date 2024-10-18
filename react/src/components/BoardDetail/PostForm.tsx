// PostForm.tsx
import React from 'react';
import { TextField, Button, Box } from '@mui/material';
import { PostFormData } from '../../@types/PostFormData';
import { User } from '../../@types/User';
import { Category } from '../../@types/Category';
import CategorySelect from "./CategorySelect";
import {commonStyles} from "../../style";

interface PostFormProps {
    postFormData: PostFormData,
    user: User | null,
    categories: Category[],
    handleSubmit: (e: React.FormEvent<HTMLFormElement>) => void,
    handleFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    setPostFormData: React.Dispatch<React.SetStateAction<PostFormData>>,
    submitLabel: string,
    selectedCategory?: string
}

const PostForm: React.FC<PostFormProps> = ({
                                               postFormData,
                                               user,
                                               categories,
                                               handleSubmit,
                                               handleFileChange,
                                               setPostFormData,
                                               submitLabel,
                                               selectedCategory
                                           }) => {
    return (
        <form onSubmit={handleSubmit}>
            <CategorySelect
                categories={categories}
                selectedCategory={postFormData.category.cateName}
                onChange={(cateName) => setPostFormData(prev => ({ ...prev, category: { cateName } }))}
            />
            <TextField
                label="제목"
                variant="outlined"
                fullWidth
                margin="normal"
                value={postFormData.title}
                onChange={(e) => setPostFormData((prev: PostFormData) => ({ ...prev, title: e.target.value }))}
                required
                sx={commonStyles} // 공통 스타일 적용
            />
            <TextField
                label="내용"
                variant="outlined"
                fullWidth
                margin="normal"
                multiline
                rows={4}
                value={postFormData.content}
                onChange={(e) => setPostFormData((prev: PostFormData) => ({ ...prev, content: e.target.value }))}
                required
                sx={commonStyles} // 공통 스타일 적용
            />
            <Box display="flex" justifyContent="end">
                <Button variant="outlined" component="label" >
                    파일 업로드
                    <input type="file" hidden onChange={handleFileChange} />
                </Button>
            </Box>
            <Box display="flex" justifyContent="space-between" mt={2}>
                <Button variant="outlined" onClick={() => window.history.back()}>뒤로가기</Button>
                <Button variant="contained" type="submit">{submitLabel}</Button>
            </Box>
        </form>
    );
};

export default PostForm;
