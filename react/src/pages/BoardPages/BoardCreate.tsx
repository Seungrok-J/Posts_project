import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {toast} from 'react-toastify';
import {useNavigate} from 'react-router-dom';
import {PostFormData} from "../../@types/PostFormData";
import useUserStore from "../../store/useUserStore";
import {Category} from "../../@types/Category";
import PostForm from "../../components/BoardDetail/PostForm";
import {Container} from "@mui/material";
import UserInfo from "../../components/BoardDetail/UserInfo";

const BoardCreate: React.FC = () => {
    const [postFormData, setPostFormData] = useState<PostFormData>({
        category: {cateName: "일상"},
        title: '',
        content: ''
    });
    const [categories, setCategories] = useState<Category[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const {isLoggedIn, user} = useUserStore();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get<Category[]>('http://127.0.0.1:8080/api/board/categories');
                setCategories(response.data);
                if (response.data.length > 0) {
                    setPostFormData(prev => ({...prev, category: response.data[0]}));
                }
            } catch (error) {
                console.log("카테고리 목록을 가져오는 데 오류가 발생했습니다.", error);
            }
        };
        fetchCategories();
    }, []);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) { // event 대신 e로 변경
            setSelectedFile(e.target.files[0]);
        }
    };

    const handleSubmit = async (e: React.FormEvent) => { // async 추가
        e.preventDefault(); // preventDefault 적용
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
            const response = await axios.post('http://127.0.0.1:8080/api/board/save', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

            const boardId = response.data.boardId;
            navigate(`/board/detail/${boardId}`);
        } catch (error) {
            console.log("게시글 작성 중 오류 발생", error);
            toast.error("게시글 작성에 실패했습니다.");
        }
    };

    return (
        <Container maxWidth="sm" sx={{mt: 4}}>
            <UserInfo user={user}/>
            <PostForm
                postFormData={postFormData}
                user={user}
                categories={categories}
                handleSubmit={handleSubmit}
                handleFileChange={handleFileChange}
                setPostFormData={setPostFormData}
                submitLabel="등록"
            />
        </Container>
    );
};

export default BoardCreate;
