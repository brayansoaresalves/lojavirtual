ALTER DATABASE lojavirtual OWNER TO postgres;

CREATE TABLE public.acesso (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.acesso OWNER TO postgres;


CREATE TABLE public.avaliacao_produto (
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    nota integer NOT NULL,
    descricao text NOT NULL
);


ALTER TABLE public.avaliacao_produto OWNER TO postgres;


CREATE TABLE public.categoria_produto (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.categoria_produto OWNER TO postgres;



CREATE TABLE public.conta_pagar (
    id bigint NOT NULL,
    data_vencimento date NOT NULL,
    desconto numeric(38,2),
    descricao character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    pessoa_id bigint NOT NULL,
    pessoa_fornecedor_id bigint NOT NULL,
    data_pagamento date,
    CONSTRAINT conta_pagar_status_check CHECK (((status)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying])::text[])))
);


ALTER TABLE public.conta_pagar OWNER TO postgres;


CREATE TABLE public.conta_receber (
    id bigint NOT NULL,
    data_vencimento date NOT NULL,
    desconto numeric(38,2),
    descricao character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    pessoa_id bigint NOT NULL,
    data_pagamento date,
    CONSTRAINT conta_receber_status_check CHECK (((status)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying])::text[])))
);


ALTER TABLE public.conta_receber OWNER TO postgres;


CREATE TABLE public.cupom (
    id bigint NOT NULL,
    valor_percentagem numeric(38,2) NOT NULL,
    codigo_desconto character varying(255) NOT NULL,
    data_validade date NOT NULL,
    valor_real numeric(38,2)
);


ALTER TABLE public.cupom OWNER TO postgres;



CREATE TABLE public.endereco (
    id bigint NOT NULL,
    bairro character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    complemento character varying(255),
    logradouro character varying(255) NOT NULL,
    numero character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    uf character varying(2) NOT NULL,
    pessoa_id bigint NOT NULL,
    CONSTRAINT endereco_tipo_check CHECK (((tipo)::text = ANY ((ARRAY['COBRANCA'::character varying, 'PESSOAL'::character varying])::text[])))
);


ALTER TABLE public.endereco OWNER TO postgres;



CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.forma_pagamento OWNER TO postgres;



CREATE TABLE public.imagem_produto (
    id bigint NOT NULL,
    imagem_miniatura text NOT NULL,
    imagem_original text NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.imagem_produto OWNER TO postgres;



CREATE TABLE public.item_venda_loja (
    id bigint NOT NULL,
    produto_id bigint NOT NULL,
    venda_loja_id bigint NOT NULL,
    quantidade numeric(38,2) NOT NULL
);


ALTER TABLE public.item_venda_loja OWNER TO postgres;



CREATE TABLE public.marca_produto (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE public.marca_produto OWNER TO postgres;



CREATE TABLE public.nota_fiscal_compra (
    id bigint NOT NULL,
    desconto numeric(38,2),
    observacao character varying(255),
    conta_pagar_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    data_compra date NOT NULL,
    numero_nota character varying(255) NOT NULL,
    serie character varying(255) NOT NULL,
    valor_icms numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL
);


ALTER TABLE public.nota_fiscal_compra OWNER TO postgres;



CREATE TABLE public.nota_fiscal_venda (
    id bigint NOT NULL,
    venda_loja_id bigint NOT NULL,
    numero character varying(255) NOT NULL,
    pdf text NOT NULL,
    serie character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    xml text NOT NULL
);


ALTER TABLE public.nota_fiscal_venda OWNER TO postgres;



CREATE TABLE public.nota_item_produto (
    id bigint NOT NULL,
    nota_fiscal_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    quantidade numeric(38,2) NOT NULL
);


ALTER TABLE public.nota_item_produto OWNER TO postgres;



CREATE TABLE public.pessoa_fisica (
    id bigint NOT NULL,
    cpf character varying(255) NOT NULL,
    data_nascimento date,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL
);


ALTER TABLE public.pessoa_fisica OWNER TO postgres;



CREATE TABLE public.pessoa_juridica (
    id bigint NOT NULL,
    categoria character varying(255),
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    cnpj character varying(255) NOT NULL,
    fantasia character varying(255) NOT NULL,
    inscricao_estadual character varying(255) NOT NULL,
    inscricao_municipal character varying(255),
    razao character varying(255) NOT NULL
);


ALTER TABLE public.pessoa_juridica OWNER TO postgres;



CREATE TABLE public.produto (
    id bigint NOT NULL,
    altura numeric(38,2) NOT NULL,
    descricao text NOT NULL,
    largura numeric(38,2) NOT NULL,
    nome character varying(255) NOT NULL,
    peso numeric(38,2) NOT NULL,
    profundidade numeric(38,2),
    quantidade_cliques integer,
    quantidade_estoque integer NOT NULL,
    unidade character varying(255) NOT NULL,
    valor_venda numeric(38,2) NOT NULL,
    ativo boolean NOT NULL,
    alerta_quantidade_estoque boolean,
    link_youtube character varying(255),
    quantidade_alerta_estoque integer
);


ALTER TABLE public.produto OWNER TO postgres;


CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_acesso OWNER TO postgres;



CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_avaliacao_produto OWNER TO postgres;



CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_categoria_produto OWNER TO postgres;


CREATE SEQUENCE public.seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_conta_pagar OWNER TO postgres;



CREATE SEQUENCE public.seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_conta_receber OWNER TO postgres;



CREATE SEQUENCE public.seq_cupom
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_cupom OWNER TO postgres;



CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_endereco OWNER TO postgres;



CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_forma_pagamento OWNER TO postgres;



CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_imagem_produto OWNER TO postgres;



CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_item_venda_loja OWNER TO postgres;



CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_marca_produto OWNER TO postgres;



CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_fiscal_compra OWNER TO postgres;



CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_fiscal_venda OWNER TO postgres;



CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_item_produto OWNER TO postgres;



CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_pessoa OWNER TO postgres;



CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_produto OWNER TO postgres;



CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_status_rastreio OWNER TO postgres;



CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_usuario OWNER TO postgres;



CREATE SEQUENCE public.seq_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_venda_loja OWNER TO postgres;



CREATE TABLE public.status_rastreio (
    id bigint NOT NULL,
    centro_distribuicao character varying(255),
    cidade character varying(255),
    estado character varying(255),
    status character varying(255),
    venda_loja_id bigint NOT NULL
);


ALTER TABLE public.status_rastreio OWNER TO postgres;



CREATE TABLE public.usuario (
    id bigint NOT NULL,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    data_atual_senha date NOT NULL,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;



CREATE TABLE public.usuarios_acessos (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);


ALTER TABLE public.usuarios_acessos OWNER TO postgres;



CREATE TABLE public.venda_loja (
    id bigint NOT NULL,
    data_entrega date,
    desconto numeric(38,2),
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    nota_fiscal_venda_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    data_venda date NOT NULL,
    dias_entrega integer NOT NULL,
    valor_frete numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    cupon_id bigint
);


ALTER TABLE public.venda_loja OWNER TO postgres;



INSERT INTO public.avaliacao_produto (id, pessoa_id, produto_id, nota, descricao) VALUES (1, 1, 1, 10, 'TESTANDO DESCRIÇÃO');




INSERT INTO public.pessoa_fisica (id, cpf, data_nascimento, email, nome, telefone) VALUES (1, '70314383140', '1996-08-18', 'brayan.iub10@gmail.com', 'BRAYAN ALVES SOARES', '64992794687');



INSERT INTO public.produto (id, altura, descricao, largura, nome, peso, profundidade, quantidade_cliques, quantidade_estoque, unidade, valor_venda, ativo, alerta_quantidade_estoque, link_youtube, quantidade_alerta_estoque) VALUES (1, 1.00, 'CANETA BIG PRETA', 1.00, 'CANETA', 1.00, 1.00, 0, 25, 'UN', 6.00, true, true, NULL, 5);




SELECT pg_catalog.setval('public.seq_acesso', 1, false);




SELECT pg_catalog.setval('public.seq_avaliacao_produto', 1, false);




SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);




SELECT pg_catalog.setval('public.seq_conta_pagar', 1, false);



SELECT pg_catalog.setval('public.seq_conta_receber', 1, false);




SELECT pg_catalog.setval('public.seq_cupom', 1, false);




SELECT pg_catalog.setval('public.seq_endereco', 1, false);




SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);




SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);




SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);




SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);



SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);




SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);




SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);




SELECT pg_catalog.setval('public.seq_pessoa', 1, false);




SELECT pg_catalog.setval('public.seq_produto', 1, false);




SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);




SELECT pg_catalog.setval('public.seq_usuario', 1, false);




SELECT pg_catalog.setval('public.seq_venda_loja', 1, false);




ALTER TABLE ONLY public.acesso
    ADD CONSTRAINT acesso_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT avaliacao_produto_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.categoria_produto
    ADD CONSTRAINT categoria_produto_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.conta_pagar
    ADD CONSTRAINT conta_pagar_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.conta_receber
    ADD CONSTRAINT conta_receber_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.cupom
    ADD CONSTRAINT cupom_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT imagem_produto_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT item_venda_loja_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.marca_produto
    ADD CONSTRAINT marca_produto_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT nota_fiscal_compra_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT nota_fiscal_venda_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_item_produto_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.pessoa_fisica
    ADD CONSTRAINT pessoa_fisica_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.pessoa_juridica
    ADD CONSTRAINT pessoa_juridica_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT status_rastreio_pkey PRIMARY KEY (id);




ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT ukaf716mdsqbrp05vo63te1xvaq UNIQUE (venda_loja_id);




ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT uknocd9ne1kh9heyr6f3ngqmgdi UNIQUE (nota_fiscal_venda_id);




ALTER TABLE ONLY public.usuarios_acessos
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);




ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT venda_loja_pkey PRIMARY KEY (id);



CREATE TRIGGER validachavepessoaatualizacaocr BEFORE UPDATE ON public.conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoaatualizacaoendereco BEFORE UPDATE ON public.endereco FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoaatualizacaonfcompra BEFORE UPDATE ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoaatualizacaousuario BEFORE UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoaatualizacaoven BEFORE UPDATE ON public.venda_loja FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoainsercaocr BEFORE INSERT ON public.conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoainsercaoendereco BEFORE INSERT ON public.endereco FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();


CREATE TRIGGER validachavepessoainsercaonfcompra BEFORE INSERT ON public.nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();



CREATE TRIGGER validachavepessoainsercaousuario BEFORE INSERT ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();



CREATE TRIGGER validachavepessoainsercaoven BEFORE INSERT ON public.venda_loja FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();



CREATE TRIGGER validachavepessoainsert BEFORE INSERT ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoainsertcpfornecedora BEFORE INSERT ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoafornecedora();




CREATE TRIGGER validachavepessoaupdate BEFORE UPDATE ON public.avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachavepessoa();




CREATE TRIGGER validachavepessoaupdatecpfornecedora BEFORE UPDATE ON public.conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachavepessoafornecedora();



ALTER TABLE ONLY public.usuarios_acessos
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.acesso(id);



ALTER TABLE ONLY public.nota_fiscal_compra
    ADD CONSTRAINT conta_pagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.conta_pagar(id);




ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT cupom_fk FOREIGN KEY (cupon_id) REFERENCES public.cupom(id);



ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT endereco_cobranca_fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.endereco(id);



ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT endereco_entrega_fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.endereco(id);



ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT forma_pagamento_id FOREIGN KEY (forma_pagamento_id) REFERENCES public.forma_pagamento(id);




ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT nota_fiscal_fk FOREIGN KEY (nota_fiscal_id) REFERENCES public.nota_fiscal_compra(id);




ALTER TABLE ONLY public.venda_loja
    ADD CONSTRAINT nota_fiscal_venda_fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.nota_fiscal_venda(id);



ALTER TABLE ONLY public.imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);




ALTER TABLE ONLY public.nota_item_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);




ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);




ALTER TABLE ONLY public.avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.produto(id);




ALTER TABLE ONLY public.usuarios_acessos
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);




ALTER TABLE ONLY public.nota_fiscal_venda
    ADD CONSTRAINT venda_loja_fk FOREIGN KEY (venda_loja_id) REFERENCES public.venda_loja(id);




ALTER TABLE ONLY public.status_rastreio
    ADD CONSTRAINT venda_loja_fk FOREIGN KEY (venda_loja_id) REFERENCES public.venda_loja(id);



ALTER TABLE ONLY public.item_venda_loja
    ADD CONSTRAINT venda_loja_fk FOREIGN KEY (venda_loja_id) REFERENCES public.venda_loja(id);

