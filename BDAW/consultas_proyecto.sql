/*
Consultas Proyecto PAY TO WIN
------------------------------

Nombre: Juan Pablo Sierra Amariles
Curso: 1¬∫DAW
*/

--Consultas de la sintaxis select b√°sica
------------------------------------------
--1. Consulta para saber que usuarios son mayores de edad. (Servir√° para recomendarles juegos seg√∫n su edad o poner restricciones).
select * from proyecto_usuario where fnac <= sysdate - 18;


--2. Consulta sobre los productos que tengan menos de 100 de stock de la base de datos.
select * from proyecto_productos where stock < 100;


--3. Imprimir pedidos que no esten facturados de la base de datos.
select * from proyecto_pedido where facturado like 'NO';



--Consultas con funciones SQL
-------------------------------
--1. Consulta para saber el usuario con m·s aÒos de la base de datos.
select * from proyecto_usuario
where trunc(MONTHS_BETWEEN(sysdate,fnac) / 12) = (select max(trunc(MONTHS_BETWEEN(sysdate,fnac) / 12)) "Edad"
from proyecto_usuario);


--2. Consulta para saber la calle o avenida m·s larga de una direcciÛn de la base de datos.
select max(calle) "Calle/Avenida" from proyecto_direccion;


--3. Consulta para saber el n˙mero de productos de la categoria videojuegos en la base de datos.
select count(p.codigo) "N∫ de Videojuegos" from proyecto_productos p, proyecto_categoria c, proyecto_clasificacionproducto cp 
where cp.productos = p.codigo and cp.categoria = c.codigo and c.nombre like 'Videojuegos';




--Consultas usando Agrupaciones, combinaciones u operaciones de conjuntos
----------------------------------------------------------------------------
--1. Consulta para saber el n˙mero de direcciones que tiene un usuario.
select d.* from proyecto_direccion d left join proyecto_usuario u on u.nombreUsuario like d.usuario where d.usuario like 'Ray';


--2. Consulta para filtrar las categorias con su stock total entre todos sus productos.
select c.nombre "Categorias", sum(p.stock) "Stock Total" from proyecto_categoria c 
left join proyecto_clasificacionproducto cp on cp.categoria = c.codigo
left join proyecto_productos p on p.codigo = cp.productos 
group by c.nombre;


--3. Consulta para imprimir los usuarios y productos que hayan nacido o sacado a la vez despues del 2000 (UNION) .
select u.nombreUsuario "Nombre", u.fnac "Fecha de Nacimiento/Lanzamiento", u.tipo "Tipo", u.foto "Foto"
from proyecto_usuario u where u.fnac >= to_date('01/01/2000','dd/mm/yyyy')
union
select p.nombre "Nombre", p.fechaCrea "Fecha de Nacimiento/Lanzamiento", c.nombre "Tipo", p.foto "Foto"
from proyecto_productos p 
left join proyecto_clasificacionproducto cp on cp.productos = p.codigo
left join proyecto_categoria c on c.codigo = cp.categoria
where p.fechaCrea >= to_date('01/01/2000','dd/mm/yyyy')
order by 2;




--Crear 1 vista, 1 insert y 1 borrado
---------------------------------------
--Vista "ProductosMasVendidos".
create or replace view "ProductosMasVendidos" (productos, ventas) as
select distinct p.nombre "Productos", max(lp.productos) "N∫ de Ventas" from proyecto_productos p
left join proyecto_lineapedido lp on lp.productos = p.codigo group by p.nombre having max(lp.productos) is not null;



--(INSERT) Introducir en el pedido 5 un ordenador.
insert into proyecto_lineapedido (pedido, productos, cantidad, precio)
select 5, codigo, 1, precio
from proyecto_productos where codigo = 5;

--(UPDATE) Actualizar que el stock minimo de todos los productos tengan que ser 5 y que el stock sume 68 para el producto que tenga menos stock de 10.
update proyecto_productos set (stockMinimo, stock) = (select stockMinimo + 5, stock + 68 from proyecto_productos where stock < 10) where stock < 10;