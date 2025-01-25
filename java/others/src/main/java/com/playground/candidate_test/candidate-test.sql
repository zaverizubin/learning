select * from post
select * from blog
select * from [post-category]
select * from category

--SQL query 1 ---Number of posts in each category including categories with no posts.
select count(postcategory.postId) as postcount, category.categoryId, category.categoryName from
category left join [postcategory] on category.categoryId = [postcategory].categoryId
group by category.categoryId, category.categoryName

--SQL query 1 alt---
select count(post.postId) as postcount, category.categoryId, category.categoryName from
    post inner join [postcategory] on post.postId = [postcategory].postId
    right join category on [postcategory].categoryId = category.categoryId
group by category.categoryId, category.categoryName

--SQL query 2--- Average number of categories used within each blog
select temp.blogId,  avg(temp.[categoryCount])  as [categoryCount] from
    (select blog.blogId, post.postId, count([postcategory].categoryId) as [categoryCount] from
    blog inner join post on blog.blogId = post.blogId
    inner join [postcategory] on [postcategory].postId = post.postId
    group by blog.blogId, post.postId) as temp
group by temp.blogId

--SQL query 3--- Select Top 3 posters (users) for each category
SELECT * FROM (
  SELECT category.categoryId, [user].userId, [user].username, count([user].userId) user_post_count,
      ROW_NUMBER() OVER (PARTITION BY category.categoryId Order by count([user].userId) DESC) AS rank# FROM
      category INNER JOIN postcategory on category.categoryId = postcategory.categoryId
      INNER JOIN post on post.postId = postcategory.postId
      INNER JOIN [user] on [user].userId = post.userId
  GROUP BY category.categoryId, [user].userId, [user].username
) temp WHERE rank# <=3
