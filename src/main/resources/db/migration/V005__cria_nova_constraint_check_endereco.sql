ALTER TABLE endereco DROP CONSTRAINT endereco_tipo_check;

ALTER TABLE endereco 
  ADD CONSTRAINT endereco_tipo_check 
  CHECK (tipo IN ('COBRANCA', 'ENTREGA'));