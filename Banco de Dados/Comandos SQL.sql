CREATE TABLE IF NOT EXISTS public.Tarefas
(
    idTarefa integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 1000000 CACHE 1 ),
    nome text COLLATE pg_catalog."default" NOT NULL,
    tarefa text COLLATE pg_catalog."default" NOT NULL,
	status text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Tarefas_pkey" PRIMARY KEY (idTarefa)
)

select * from Tarefas

INSERT INTO Tarefas (nome, tarefa, status) VALUES ('', '', '')

UPDATE Tarefas SET nome = '', tarefa= '', status = '' WHERE idTarefa = 1

DELETE FROM Tarefas WHERE Tarefas.idTarefa = 1

Drop table Tarefas