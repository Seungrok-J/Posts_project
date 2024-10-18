import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useNavigate, useParams } from 'react-router-dom';
import { Container, Box, Button } from '@mui/material';
import CategorySelect from "../../components/BoardDetail/CategorySelect";
import useUserStore from "../../store/useUserStore";
import { Board } from "../../@types/Board";
import { Category } from "../../@types/Category";
import { PostFormData } from "../../@types/PostFormData";
import UserInfo from "../../components/BoardDetail/UserInfo";
import PostForm from "../../components/BoardDetail/PostForm"; // PostForm 컴포넌트 가져오기

const BoardUpdate: React.FC = () => {
    const { id: boardId } = useParams<{ id: string }>();
    const [postFormData, setPostFormData] = useState<PostFormData>({
        category: { cateName: "" },
        title: '',
        content: '',
    });
    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const { isLoggedIn, user } = useUserStore();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [categoriesResponse, postResponse] = await Promise.all([
                    axios.get<Category[]>('http://127.0.0.1:8080/api/board/categories'),
                    axios.get<Board>(`http://127.0.0.1:8080/api/board/detail/${boardId}`)
                ]);
                setCategories(categoriesResponse.data);
                setPostFormData({
                    category: { cateName: postResponse.data.category.cateName },
                    title: postResponse.data.title,
                    content: postResponse.data.content,
                });
            } catch (error) {
                console.log("데이터를 가져오는 데 오류가 발생했습니다.", error);
                toast.error("데이터를 가져오는 데 오류가 발생했습니다.");
            }
        };
        fetchData();
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

        if (!postFormData.category.cateName) {
            toast.error("카테고리를 선택해주세요.");
            return;
        }

        const formData = new FormData();
        formData.append('userId', user?.userId.toString() || '');
        formData.append('title', postFormData.title);
        formData.append('content', postFormData.content);
        formData.append('categoryName', postFormData.category.cateName);
        formData.append('nickname', user?.nickName || '');
        formData.append('name', user?.userName || '');

        if (selectedFile) {
            formData.append('file', selectedFile);
        }

        try {
            const response = await axios.put(`http://127.0.0.1:8080/api/board/update/${boardId}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            navigate(`/board/detail/${boardId}`);
        } catch (error) {
            console.log("게시글 수정 중 오류 발생", error);
            toast.error("게시글 수정에 실패했습니다.");
        }
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 4 }}>
            <UserInfo user={user} />
            <PostForm
                postFormData={postFormData} // postFormData 전달
                handleFileChange={handleFileChange} // 파일 변경 핸들러 전달
                handleSubmit={handleSubmit} // 제출 핸들러 전달
                categories={categories} // 카테고리 전달
                selectedCategory={postFormData.category.cateName} // 선택된 카테고리 전달
                setPostFormData={setPostFormData} // setPostFormData 전달
                submitLabel="수정" // 버튼 레이블
                user={user}/>
        </Container>
    );
};

export default BoardUpdate;
