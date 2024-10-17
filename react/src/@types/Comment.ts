export interface Comment {
    like_count: number;
    board_id: number;
    comments_id: number;
    created_at: string;
    parent_comment_id: number | null;
    updated_at: string | null;
    user_id: number;
    content: string;
}
