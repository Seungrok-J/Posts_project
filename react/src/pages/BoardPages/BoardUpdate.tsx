import React, { useEffect, useState } from 'react';
import { Category } from "../../types/Category";
import axios from "axios";
import { toast } from "react-toastify";
import useUserStore from "../../store/useUserStore";
import {
    Container,
    TextField,
    Select,
    MenuItem,
    Button,
    InputLabel,
    FormControl,
    Box,
} from '@mui/material';
import { useParams } from "react-router-dom";

const BoardUpdate = () => {
    const { boardId } = useParams<{ boardId: string }>();
    const [postFormData, setPostFormData] = useState({
        category: {
            cateId: 1,
        },
        title: '',
        content: '',
    });
    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const { isLoggedIn } = useUserStore();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('http://127.0.0.1:8080/api/board/categories');
                setCategories(response.data);
            } catch (error) {
                console.error("카테고리 목록을 가져오는 데 오류가 발생했습니다.", error);
            }
        };
        fetchCategories();
    }, []);

    useEffect(() => {
        const fetchBoardDetail = async () => {
            if (boardId) {
                try {
                    const response = await axios.get(`http://127.0.0.1:8080/api/board/detail/${boardId}`);
                    const { title, content, category } = response.data;
                    setPostFormData({
                        title,
                        content,
                        category: { cateId: category.cateId },
                    });
                } catch (error) {
                    console.error("게시글 상세 정보를 가져오는 데 오류가 발생했습니다.", error);
                }
            }
        };
        fetchBoardDetail();
    }, [boardId]);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            setSelectedFile(event.target.files[0]);
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (!isLoggedIn) {
            toast.error("인증이 필요합니다. 다시 로그인해 주세요.");
            return;
        }

        const formData = new FormData();
        formData.append('title', postFormData.title);
        formData.append('content', postFormData.content);
        formData.append('categoryId', postFormData.category.cateId.toString());

        if (selectedFile) {
            formData.append('file', selectedFile);
        }

        try {
            const response = await axios.put(`http://127.0.0.1:8080/api/board/update/${boardId}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            const updatedBoardId = response.data;
            window.location.href = `/board/detail/${updatedBoardId}`;
        } catch (error) {
            console.error("게시글 수정 중 오류 발생", error);
        }
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 4 }}>
            <form onSubmit={handleSubmit}>
                <FormControl fullWidth margin="normal">
                    <InputLabel id="cate-select-label">카테고리</InputLabel>
                    <Select
                        sx={{ marginTop: 2 }}
                        labelId="cate-select-label"
                        value={postFormData.category.cateId.toString()}
                        onChange={(e) => setPostFormData(prev => ({
                            ...prev,
                            category: { cateId: parseInt(e.target.value) }
                        }))}>
                        <MenuItem value="default" disabled>
                            선택하세요.
                        </MenuItem>
                        {categories.map((item) => (
                            <MenuItem key={item.cateId} value={item.cateId}>
                                {item.cateName}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <TextField
                    label="제목"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={postFormData.title}
                    onChange={(e) => setPostFormData(prev => ({
                        ...prev,
                        title: e.target.value
                    }))}
                    required
                />

                <TextField
                    label="내용"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    multiline
                    rows={4}
                    value={postFormData.content}
                    onChange={(e) => setPostFormData(prev => ({
                        ...prev,
                        content: e.target.value
                    }))}
                    required
                />
                <Box display="flex" justifyContent="end">
                    <Button
                        variant="outlined"
                        component="label"
                        sx={{ marginTop: 2 }}>
                        파일 업로드
                        <input type="file" hidden onChange={handleFileChange} />
                    </Button>
                </Box>

                <Box display="flex" justifyContent="space-between" mt={2}>
                    <Button
                        variant="outlined"
                        onClick={() => window.history.back()}
                    >
                        뒤로가기
                    </Button>
                    <Button
                        variant="contained"
                        type="submit"
                    >
                        등록
                    </Button>
                </Box>
            </form>
        </Container>
    );
};

export default BoardUpdate;
