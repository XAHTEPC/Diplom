PGDMP  1    7                 }            Diplom1    12.20    16.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    32883    Diplom1    DATABASE     }   CREATE DATABASE "Diplom1" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "Diplom1";
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false                       0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    6            �            1259    32893    access    TABLE     �   CREATE TABLE public.access (
    id_access integer NOT NULL,
    taiga character varying(10) DEFAULT false NOT NULL,
    git character varying(10) DEFAULT false NOT NULL,
    id_user integer NOT NULL
);
    DROP TABLE public.access;
       public         heap    postgres    false    6            �            1259    32891    access_id_access_seq    SEQUENCE     �   ALTER TABLE public.access ALTER COLUMN id_access ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.access_id_access_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    6    205            �            1259    32886    users    TABLE     �   CREATE TABLE public.users (
    id_user integer NOT NULL,
    login character varying(100) NOT NULL,
    pass_salt character varying(88) NOT NULL,
    id_comp character varying(88) NOT NULL,
    name character varying(150) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false    6            �            1259    32884    users_id_user_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id_user ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    6    203                      0    32893    access 
   TABLE DATA           @   COPY public.access (id_access, taiga, git, id_user) FROM stdin;
    public          postgres    false    205   �                 0    32886    users 
   TABLE DATA           I   COPY public.users (id_user, login, pass_salt, id_comp, name) FROM stdin;
    public          postgres    false    203   �                  0    0    access_id_access_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.access_id_access_seq', 1, false);
          public          postgres    false    204                       0    0    users_id_user_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.users_id_user_seq', 2, true);
          public          postgres    false    202            �
           2606    32899    access access_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.access
    ADD CONSTRAINT access_pkey PRIMARY KEY (id_access);
 <   ALTER TABLE ONLY public.access DROP CONSTRAINT access_pkey;
       public            postgres    false    205            �
           2606    32890    users users_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    203            �
           2606    32900    access user    FK CONSTRAINT     q   ALTER TABLE ONLY public.access
    ADD CONSTRAINT "user" FOREIGN KEY (id_user) REFERENCES public.users(id_user);
 7   ALTER TABLE ONLY public.access DROP CONSTRAINT "user";
       public          postgres    false    203    2696    205                  x������ � �         �  x�e�͎�P ��^��iHG�Q`1IEPP��h��G��s0�I��=L2I/��M3I��ζ�~�7�C��}i�8��T��T	x�p�0�ybn��N�b��'��j/�g;V��ت6O���ǵl&���H	�ͣ
ϱzZXJ4���ج&��}��=�3�+�$���.�UT�5y�[ޑ�g�Gڮ!l�I*���l�F5�][k�ÕE��,�e���{�<u��[�6�~t?�_ݟ����{ps�!@U�M?����[����;:9Y��g�*)ҩ�;��Q�m֤\���<�����dT����+s��+�I,
hq,�&�Y*�-e�Y��q}�6*k���b��-���7����1j��l���`�5Mu�AgO9��Dw�H
��p�p�?��
�v�:�� 9Y�N��/��~�/H��o     