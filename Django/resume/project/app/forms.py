from django import forms
from django.forms import fields
from .models import INFO,skill,experince,edu,DP,project

class form(forms.ModelForm):
    class Meta:
        model = INFO
        fields = ['name','desi' ,'about','age','email','phone','address','lang' ]