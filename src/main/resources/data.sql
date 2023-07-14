INSERT INTO t_roles (id, name) VALUES (1, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO t_roles (id, name) VALUES (2, 'USER') ON CONFLICT DO NOTHING;

INSERT INTO users (id, name, lastname, age, username, password, active)
VALUES (1, 'admin', 'admin', 100, 'admin', 'admin', true) ON CONFLICT DO NOTHING;

INSERT INTO user_role (user_id, role_id) VALUES (1, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_role (user_id, role_id) VALUES (1, 2) ON CONFLICT DO NOTHING;
