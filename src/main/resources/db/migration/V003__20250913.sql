alter table avaliacao_produto add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table conta_pagar add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table categoria_produto add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table conta_receber add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table cupom add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table endereco add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table forma_pagamento add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table imagem_produto add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table item_venda_loja add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table marca_produto add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table nota_fiscal_compra add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table nota_fiscal_venda add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table nota_item_produto add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table pessoa_fisica add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table pessoa_juridica add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table produto add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table status_rastreio add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table usuario add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;

alter table venda_loja add constraint empresa_fk foreign key (empresa_id) references pessoa_juridica;
