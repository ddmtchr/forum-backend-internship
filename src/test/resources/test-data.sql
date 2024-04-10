INSERT INTO topics (id, created, name) VALUES
    ('89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6', '2024-12-04T15:33:17+03:00', 'Topic 1'),
    ('16e08225-d756-4b29-b754-a5417a1a8c7f', '2024-12-05T15:23:17+03:00', 'Topic 2');

INSERT INTO messages (id, author, created, text, topic_id) VALUES
    ('9ded9493-64cb-4b4a-ac57-0d0732282d42', 'Author 1', '2024-12-03T15:33:17+03:00', 'Message 1', '89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6'),
    ('d5228be5-d9d1-4a93-9aa1-d28b950666d8', 'Author 2', '2024-12-03T15:23:15+03:00', 'Message 2', '89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6'),
    ('3f7f71aa-9911-4392-8091-900d44d13a12', 'Author 3', '2024-12-03T15:23:17+03:00', 'Message 3', '89a0ecb0-2fdf-4aae-8761-40f3f5bca6c6'),
    ('d19a3d3b-e522-4f56-9a4a-4b9d8d9b2dfe', 'Author 4', '2024-12-03T15:23:07+03:00', 'Message 4', '16e08225-d756-4b29-b754-a5417a1a8c7f'),
    ('f20ac031-2f73-4a1e-9130-44a95c4ccc73', 'Author 5', '2024-12-03T05:23:17+03:00', 'Message 5', '16e08225-d756-4b29-b754-a5417a1a8c7f');
