import React, {useEffect, useState} from 'react';
import {Category} from "../../types/Category";
import axios from "axios";
import {toast} from "react-toastify";
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

const BoardCreate = () => {
    const [postFormData, setPostFormData] = useState({
        category: {
            cateId: 0,  // cateId를 기본값으로 설정
        },
        title: '',
        content: '',
    });

    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const {isLoggedIn} = useUserStore();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('http://127.0.0.1:8080/api/board/categories');
                setCategories(response.data);
            } catch (error) {
                console.log("카테고리 목록을 가져오는 데 오류가 발생했습니다.", error);
            }
        };
        fetchCategories();
    }, []);

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

        // const token = sessionStorage.getItem('token');

        try {
            const response = await axios.post('http://127.0.0.1:8080/api/board/save', formData, {
                method: "POST",
                headers: {
                    'Content-Type': 'multipart/form-data',
                    // 'Authorization': `Bearer ${token}`,

                },
            });

            const boardId = response.data;

            window.location.href = `/board/detail/${boardId}`;
        } catch (error) {
            console.log("게시글 작성 중 오류 발생", error);
            toast.error("게시글 작성에 실패했습니다.");
        }
    };

    return (
        <Container maxWidth="sm" sx={{mt: 4}}>
            <form onSubmit={handleSubmit}>
                <FormControl fullWidth margin="normal">
                    <InputLabel id="cate-select-label">카테고리</InputLabel>
                    <Select
                        sx={{marginTop: 2}}
                        labelId="cate-select-label"
                        defaultValue="default"
                        onChange={(e) => setPostFormData(prev => ({
                            ...prev,
                            category: {cateId: parseInt(e.target.value)}
                        }))}
                    >
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
                        sx={{marginTop: 2}} // sx 속성을 사용하여 마진 설정
                    >
                        파일 업로드
                        <input type="file" hidden onChange={handleFileChange}/>
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

export default BoardCreate;
