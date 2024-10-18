import React, { useEffect, useState } from 'react';
import { Category } from "../../@types/Category";
import axios from "axios";
import { toast } from "react-toastify";
import useUserStore from "../../store/useUserStore";
import {
    Container,
    Typography,
    TextField,
    Select,
    MenuItem,
    Button,
    InputLabel,
    FormControl,
    Box,
} from '@mui/material';
import { useNavigate, useParams } from "react-router-dom";

const BoardUpdate = () => {
    const { id } = useParams<{ id: string }>();
    const [postFormData, setPostFormData] = useState({
        category: {
            cateName: "",
        },
        title: '',
        content: '',
    });

    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const { isLoggedIn, user } = useUserStore();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('http://127.0.0.1:8080/api/board/categories');
                setCategories(response.data);
                // 기본적으로 첫 번째 카테고리로 설정
                if (response.data.length > 0) {
                    setPostFormData(prev => ({
                        ...prev,
                        category: { cateName: response.data[0].cateName }
                    }));
                }
            } catch (error) {
                console.log("카테고리 목록을 가져오는 데 오류가 발생했습니다.", error);
            }
        };

        const fetchPostDetails = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/api/board/detail/${id}`);
                setPostFormData({
                    category: { cateName: response.data.category.cateName },
                    title: response.data.title,
                    content: response.data.content,
                });
            } catch (error) {
                console.log("게시글 정보를 가져오는 데 오류가 발생했습니다.", error);
                toast.error("게시글 정보를 가져오는 데 오류가 발생했습니다.");
            }
        };

        fetchCategories();
        fetchPostDetails();
    }, [id]);

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
        formData.append('userId', user?.userId.toString() || '');
        formData.append('title', postFormData.title);
        formData.append('content', postFormData.content);
        formData.append('categoryName', postFormData.category.cateName);

        if (selectedFile) {
            formData.append('file', selectedFile);
        }

        try {
            await axios.put(`http://127.0.0.1:8080/api/board/update/${id}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            toast.success("게시글이 업데이트되었습니다.");
            navigate(`/board/detail/${id}`);
        } catch (error) {
            console.log("게시글 업데이트 중 오류 발생", error);
            toast.error("게시글 업데이트에 실패했습니다.");
        }
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 4 }}>
            <Typography variant="h6" gutterBottom>
                사용자 정보
            </Typography>
            <Typography>닉네임: {user?.nickName}</Typography>
            <Typography>이름: {user?.userName}</Typography>

            <form onSubmit={handleSubmit}>
                <FormControl fullWidth margin="normal">
                    <InputLabel id="cate-select-label">카테고리</InputLabel>
                    <Select
                        labelId="cate-select-label"
                        value={postFormData.category.cateName}
                        onChange={(e) => setPostFormData(prev => ({
                            ...prev,
                            category: { cateName: e.target.value }
                        }))}
                    >
                        <MenuItem value="default" disabled>
                            선택하세요.
                        </MenuItem>
                        {categories.map((item) => (
                            <MenuItem key={item.cateName} value={item.cateName}>
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
                        sx={{ marginTop: 2 }}
                    >
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
                        수정
                    </Button>
                </Box>
            </form>
        </Container>
    );
};

export default BoardUpdate;
