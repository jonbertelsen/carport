--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.1

-- Started on 2024-05-07 20:07:49 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 41149)
-- Name: order_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_item (
                                   order_item_id integer NOT NULL,
                                   order_id integer NOT NULL,
                                   product_variant_id integer NOT NULL,
                                   quantity integer NOT NULL,
                                   description character varying(100) NOT NULL
);


ALTER TABLE public.order_item OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 41141)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
                               order_id integer NOT NULL,
                               carport_width integer NOT NULL,
                               carport_length integer NOT NULL,
                               status integer DEFAULT 0 NOT NULL,
                               user_id integer NOT NULL,
                               total_price integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 41156)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
                                product_id integer NOT NULL,
                                name character varying(100) NOT NULL,
                                unit character varying NOT NULL,
                                price integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 41165)
-- Name: product_variant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_variant (
                                        product_variant_id integer NOT NULL,
                                        length integer DEFAULT 0 NOT NULL,
                                        product_id integer NOT NULL
);


ALTER TABLE public.product_variant OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 41199)
-- Name: bill_of_materials_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.bill_of_materials_view AS
SELECT product_variant.product_id,
       io.product_variant_id,
       orders.order_id,
       orders.carport_width,
       orders.carport_length,
       orders.status,
       orders.user_id,
       orders.total_price,
       io.order_item_id,
       io.quantity,
       io.description,
       product_variant.length,
       product.name,
       product.unit,
       product.price
FROM (((public.orders
    JOIN public.order_item io USING (order_id))
    JOIN public.product_variant USING (product_variant_id))
    JOIN public.product USING (product_id));


ALTER VIEW public.bill_of_materials_view OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 41148)
-- Name: order_item_order_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_item_order_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_item_order_item_id_seq OWNER TO postgres;

--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 219
-- Name: order_item_order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_item_order_item_id_seq OWNED BY public.order_item.order_item_id;


--
-- TOC entry 217 (class 1259 OID 41140)
-- Name: orders_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_order_id_seq OWNER TO postgres;

--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 217
-- Name: orders_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_order_id_seq OWNED BY public.orders.order_id;


--
-- TOC entry 221 (class 1259 OID 41155)
-- Name: product_product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_product_id_seq OWNER TO postgres;

--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 221
-- Name: product_product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_product_id_seq OWNED BY public.product.product_id;


--
-- TOC entry 223 (class 1259 OID 41164)
-- Name: product_variant_product_variant_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_variant_product_variant_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.product_variant_product_variant_id_seq OWNER TO postgres;

--
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 223
-- Name: product_variant_product_variant_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_variant_product_variant_id_seq OWNED BY public.product_variant.product_variant_id;


--
-- TOC entry 215 (class 1259 OID 41130)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 41131)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              user_id integer DEFAULT nextval('public.users_user_id_seq'::regclass) NOT NULL,
                              username character varying(50) NOT NULL,
                              password character varying(50),
                              role character varying(20) DEFAULT 'user'::character varying NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3232 (class 2604 OID 41152)
-- Name: order_item order_item_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_item ALTER COLUMN order_item_id SET DEFAULT nextval('public.order_item_order_item_id_seq'::regclass);


--
-- TOC entry 3229 (class 2604 OID 41144)
-- Name: orders order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN order_id SET DEFAULT nextval('public.orders_order_id_seq'::regclass);


--
-- TOC entry 3233 (class 2604 OID 41159)
-- Name: product product_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN product_id SET DEFAULT nextval('public.product_product_id_seq'::regclass);


--
-- TOC entry 3235 (class 2604 OID 41168)
-- Name: product_variant product_variant_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_variant ALTER COLUMN product_variant_id SET DEFAULT nextval('public.product_variant_product_variant_id_seq'::regclass);


--
-- TOC entry 3402 (class 0 OID 41149)
-- Dependencies: 220
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.order_item VALUES (1, 1, 1, 6, 'Stolper nedgraves 90 cm. i jord');
INSERT INTO public.order_item VALUES (2, 1, 7, 2, 'Remme i sider, sadles ned i stolper');
INSERT INTO public.order_item VALUES (3, 1, 7, 15, 'Spær, monteres på rem');
INSERT INTO public.order_item VALUES (4, 1, 5, 1, 'Remme i sider, sadles ned i stolper (skur del, deles)');
INSERT INTO public.order_item VALUES (5, 12, 2, 15, 'rafters');
INSERT INTO public.order_item VALUES (6, 12, 4, 2, 'beams');
INSERT INTO public.order_item VALUES (7, 12, 1, 6, 'posts');
INSERT INTO public.order_item VALUES (8, 15, 1, 6, 'Stolper nedgraves 90 cm. i jord');


--
-- TOC entry 3400 (class 0 OID 41141)
-- Dependencies: 218
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orders VALUES (1, 600, 780, 1, 1, 14890);
INSERT INTO public.orders VALUES (2, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (3, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (4, 600, 720, 1, 1, 19999);
INSERT INTO public.orders VALUES (5, 600, 720, 1, 1, 19999);
INSERT INTO public.orders VALUES (6, 600, 720, 1, 1, 19999);
INSERT INTO public.orders VALUES (7, 600, 720, 1, 1, 19999);
INSERT INTO public.orders VALUES (8, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (9, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (10, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (11, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (12, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (13, 600, 660, 1, 1, 19999);
INSERT INTO public.orders VALUES (14, 480, 520, 1, 1, 19999);
INSERT INTO public.orders VALUES (15, 600, 780, 1, 1, 19999);


--
-- TOC entry 3404 (class 0 OID 41156)
-- Dependencies: 222
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product VALUES (1, '97x97 mm. trykimp. Stolpe', 'stk', 82);
INSERT INTO public.product VALUES (2, '45x195 mm. spærtræ ubh.', 'stk', 37);


--
-- TOC entry 3406 (class 0 OID 41165)
-- Dependencies: 224
-- Data for Name: product_variant; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product_variant VALUES (1, 300, 1);
INSERT INTO public.product_variant VALUES (2, 300, 2);
INSERT INTO public.product_variant VALUES (3, 360, 2);
INSERT INTO public.product_variant VALUES (4, 420, 2);
INSERT INTO public.product_variant VALUES (5, 480, 2);
INSERT INTO public.product_variant VALUES (6, 540, 2);
INSERT INTO public.product_variant VALUES (7, 600, 2);


--
-- TOC entry 3398 (class 0 OID 41131)
-- Dependencies: 216
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (1, 'jon', '1234', 'user');


--
-- TOC entry 3417 (class 0 OID 0)
-- Dependencies: 219
-- Name: order_item_order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_item_order_item_id_seq', 8, true);


--
-- TOC entry 3418 (class 0 OID 0)
-- Dependencies: 217
-- Name: orders_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_order_id_seq', 15, true);


--
-- TOC entry 3419 (class 0 OID 0)
-- Dependencies: 221
-- Name: product_product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_product_id_seq', 2, true);


--
-- TOC entry 3420 (class 0 OID 0)
-- Dependencies: 223
-- Name: product_variant_product_variant_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_variant_product_variant_id_seq', 7, true);


--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 1, true);


--
-- TOC entry 3244 (class 2606 OID 41154)
-- Name: order_item order_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_pkey PRIMARY KEY (order_item_id);


--
-- TOC entry 3242 (class 2606 OID 41147)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_id);


--
-- TOC entry 3246 (class 2606 OID 41163)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (product_id);


--
-- TOC entry 3248 (class 2606 OID 41171)
-- Name: product_variant product_variant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_variant
    ADD CONSTRAINT product_variant_pkey PRIMARY KEY (product_variant_id);


--
-- TOC entry 3238 (class 2606 OID 41137)
-- Name: users user_name_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_name_unique UNIQUE (username);


--
-- TOC entry 3240 (class 2606 OID 41139)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3250 (class 2606 OID 41177)
-- Name: order_item order_item_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(order_id) NOT VALID;


--
-- TOC entry 3251 (class 2606 OID 41182)
-- Name: order_item order_item_product_variant_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_product_variant_id_fkey FOREIGN KEY (product_variant_id) REFERENCES public.product_variant(product_variant_id) NOT VALID;


--
-- TOC entry 3249 (class 2606 OID 41172)
-- Name: orders orders_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;


--
-- TOC entry 3252 (class 2606 OID 41187)
-- Name: product_variant product_variant_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_variant
    ADD CONSTRAINT product_variant_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.product(product_id) NOT VALID;


-- Completed on 2024-05-07 20:07:49 UTC

--
-- PostgreSQL database dump complete
--

