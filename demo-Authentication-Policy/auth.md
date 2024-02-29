基于角色的访问控制（RBAC）：在这种模式中，权限不直接分配给用户，而是分配给角色（例如管理员、编辑、访客等），然后将角色分配给用户。这种模式易于管理，特别是在用户数量较多的情况下。

访问控制列表（ACL）：在这种模式中，每个资源（例如文件、页面等）都有一个访问控制列表，列出了可以访问该资源的用户或角色。

基于属性的访问控制（ABAC）：在这种模式中，权限基于属性（例如用户的年龄、时间、位置等）来分配。这种模式非常灵活，但可能较难管理。

基于声明的访问控制（CBAC）：在这种模式中，权限基于用户的声明（例如用户的职位、部门等）来分配。这种模式可以处理复杂的权限需求，但可能需要更多的存储空间。

基于分级的访问控制（HBAC）：在这种模式中，权限基于用户在组织结构中的位置（例如用户的级别、部门等）来分配。这种模式适用于具有明确分级结构的组织。

基于令牌的访问控制（TBAC）：在这种模式中，用户需要一个令牌（例如 JWT）来访问资源。令牌中包含了用户的权限信息。

# SpringSecurity
它在权限设计上主要采用的是RBAC（Role-Based Access Control）模式。在Spring Security中，你可以定义角色（Role）和权限（Permission），并将它们分配给用户。用户通过拥有特定的角色或权限来获得对受保护资源的访问权限。

# 腾讯云
CAM（Cloud Access Management）：
提供商： 腾讯云。
用途： 主要用于管理用户对腾讯云服务的访问权限，包括对云资源的操作和管理。
特点： CAM 提供了类似于 IAM（Identity and Access Management）的功能，支持创建用户、角色和策略，以实现对腾讯云资源的细粒度控制。CAM 的设计与 RBAC 相似，通过角色和策略的方式进行权限控制。户可以创建具有特定权限的临时安全令牌，并将这些令牌提供给需要临时访问资源的用户或应用程序。

# 微软
Azure AD（Azure Active Directory）：
提供商： 微软 Azure。
用途： 主要用于管理和验证用户对 Microsoft 365 服务、Azure 云服务以及其他 Microsoft 服务的访问权限。
特点： 支持单一登录（SSO）、多因素身份验证（MFA）等身份验证功能。提供了广泛的身份和访问管理功能，包括 RBAC（基于角色的访问控制）。


# ACL


在设计业务表数据的权限时，一种常见的做法是使用一张表存储权限信息，同时在业务数据表中增加字段来表示数据的访问权限。以下是一个简单的示例，假设有一个名为Product的业务表：

1. 权限表（permissions表）：
   sql
   Copy code
   CREATE TABLE permissions (
   id INT PRIMARY KEY,
   name VARCHAR(255) NOT NULL
   );
   这个表用于存储系统中定义的各种权限。例如，可以插入以下记录：

sql
Copy code
INSERT INTO permissions (id, name) VALUES
(1, 'READ_PRODUCT'),
(2, 'WRITE_PRODUCT'),
(3, 'DELETE_PRODUCT');
2. 业务表（product表）：
   sql
   Copy code
   CREATE TABLE product (
   id INT PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   price DECIMAL(10, 2) NOT NULL,
   owner_id INT NOT NULL,
   permission_id INT NOT NULL,
   FOREIGN KEY (owner_id) REFERENCES users(id),
   FOREIGN KEY (permission_id) REFERENCES permissions(id)
   );
   在这个例子中，product表中有一个permission_id字段，它关联到permissions表，表示每个产品的权限。owner_id字段可能关联到用户表，表示数据所有者。

3. 用户权限表（user_permissions表）：
   如果你需要为每个用户分配不同的数据权限，你可以创建一个表来存储用户与权限之间的关系：

sql
Copy code
CREATE TABLE user_permissions (
user_id INT NOT NULL,
permission_id INT NOT NULL,
PRIMARY KEY (user_id, permission_id),
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (permission_id) REFERENCES permissions(id)
);