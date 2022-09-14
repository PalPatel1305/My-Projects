from django.db import models

# Create your models here.
class INFO(models.Model):
    name = models.CharField(max_length=50)
    desi = models.CharField(max_length=50)
    about = models.CharField(max_length=500)
    age = models.IntegerField()
    email = models.CharField(max_length=50)
    phone  = models.CharField(max_length=13)
    address = models.CharField(max_length=500)
    lang = models.CharField(max_length=50)

    def __str__(self):
        return self.name

class skill(models.Model):
    sub = models.CharField(max_length=10)
    cap = models.CharField(max_length=4)

    def __str__(self):
        return self.sub

class experince(models.Model):
    ex_due = models.CharField(max_length=50)
    ex_about = models.CharField(max_length=500)
    ex_post = models.CharField(max_length=50)
    ex_deg = models.CharField(max_length=50)

    def __str__(self):
        return self.ex_post

class edu(models.Model):
    due = models.CharField(max_length=20)
    degree = models.CharField(max_length=50)
    university = models.CharField(max_length=100)
    college = models.CharField(max_length=100)
    persentage = models.CharField(max_length=4)

    def __str__(self):
        return self.degree



class DP(models.Model):
    dp = models.ImageField(upload_to='images/')


class project(models.Model):
    pr_name = models.CharField(max_length=100)
    pr_about = models.CharField(max_length=500)

    def __str__(self):
        return self.pr_name



# Create your models here.
